package com.jellerijk.projects.learning.tools.kanban.domain.task;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.jellerijk.projects.learning.tools.kanban.logging.Logger;
import com.jellerijk.projects.learning.tools.kanban.persistence.database.DBController;
import com.jellerijk.projects.learning.tools.kanban.persistence.dto.TaskDTO;
import com.jellerijk.projects.learning.tools.kanban.persistence.inserters.TaskInserter;
import com.jellerijk.projects.learning.tools.kanban.persistence.loaders.TaskLoader;
import com.jellerijk.projects.learning.tools.kanban.utils.PublishedMessageType;
import com.jellerijk.projects.learning.tools.kanban.utils.Subscriber;

public class TaskControllerImpl implements TaskController {
	private TaskRepository taskRepo;

	private final int boardId;
	private final List<Subscriber> subs;

	public TaskControllerImpl(int boardId) {
		this.boardId = boardId;
		this.subs = new ArrayList<Subscriber>();
		List<Task> tasks = TaskLoader.loadTasksForBoard(boardId).stream().map(dto -> new TaskImpl(dto))
				.collect(Collectors.toCollection(ArrayList::new));
		this.taskRepo = new TaskRepositoryImpl(tasks);
	}

	@Override
	public int createTask(TaskDTO data) {
		try {
			data = TaskDTO.create(-1, data.description(), boardId, data.stageNumber(), false);
			TaskInserter.insert(data);
			int id = DBController.getInstance().getLastInserted("Task");
			addTask(TaskLoader.get(id));
			notifySubs(PublishedMessageType.REPO_UPDATE);
			return id;
		} catch (SQLException ex) {
			Logger.logError("Something went wrong while creating the task.");
			Logger.logError(ex);
		}
		throw new IllegalArgumentException("Something went wrong while creating the new task.");
	}

	@Override
	public void moveTask(int id, int stageNumber) {
		Task task = taskRepo.getTask(id);
		task.move(stageNumber);
		notifySubs(PublishedMessageType.REPO_UPDATE);
	}

	@Override
	public void updateTask(int id, TaskDTO data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteTask(int id) {
		taskRepo.remove(id);
		notifySubs(PublishedMessageType.REPO_UPDATE);
	}

	@Override
	public void completeTask(int id) {
		Task task = taskRepo.getTask(id);
		task.complete();
	}

	@Override
	public TaskDTO getTask(int id) {
		Task task = taskRepo.getTask(id);
		return TaskDTO.convert(task);
	}

	@Override
	public List<TaskDTO> getTasksForStage(int stageNumber) {
		return taskRepo.getTasksByStage(boardId, stageNumber).stream().map(task -> TaskDTO.convert(task))
				.collect(Collectors.toCollection(ArrayList::new));
	}

	@Override
	public List<Integer> getTaskIds(int stageNumber) {
		return taskRepo.getTasksByStage(boardId, stageNumber).stream().map(task -> task.getId())
				.collect(Collectors.toCollection(ArrayList::new));
	}

//	HELPER METHODS

	/**
	 * Adds a Task to the repo.
	 * 
	 * @param data
	 */
	private void addTask(TaskDTO data) {
		Task task = new TaskImpl(data);
		taskRepo.addTask(task);
	}

	@Override
	public Collection<Subscriber> getSubscribers() {
		return subs;
	}

	@Override
	public void subscribeToTask(Subscriber sub, int id) {
		Task task = taskRepo.getTask(id);
		task.subscribe(sub);
	}

}
