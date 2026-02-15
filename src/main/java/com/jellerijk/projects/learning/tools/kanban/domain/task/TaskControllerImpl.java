package com.jellerijk.projects.learning.tools.kanban.domain.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.jellerijk.projects.learning.tools.kanban.logging.Logger;
import com.jellerijk.projects.learning.tools.kanban.persistence.dto.TaskDTO;
import com.jellerijk.projects.learning.tools.kanban.utils.PublishedMessageType;
import com.jellerijk.projects.learning.tools.kanban.utils.Subscriber;

public class TaskControllerImpl implements TaskController {
	private TaskRepository taskRepo;

	private final int boardId;
	private final List<Subscriber> subs;

	public TaskControllerImpl(int boardId) {
		this.boardId = boardId;
		this.subs = new ArrayList<Subscriber>();
		this.taskRepo = new TaskRepositoryImpl();
	}

	@Override
	public void createTask(TaskDTO data) {
		try {
			Task task = new TaskImpl(data.description(), boardId, data.stageNumber(), false);
			taskRepo.addTask(task);
			notifySubs(PublishedMessageType.REPO_UPDATE);
		} catch (Exception ex) {
			Logger.logError("Something went wrong while creating the task.");
			Logger.logError(ex);
		}
	}

	@Override
	public void moveTask(int id, int stageNumber) {
		Task task = taskRepo.getTask(id);
		task.move(stageNumber);
		notifySubs(PublishedMessageType.REPO_UPDATE);
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
