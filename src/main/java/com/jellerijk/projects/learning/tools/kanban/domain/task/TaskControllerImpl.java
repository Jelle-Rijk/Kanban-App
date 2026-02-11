package com.jellerijk.projects.learning.tools.kanban.domain.task;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.jellerijk.projects.learning.tools.kanban.logging.Logger;
import com.jellerijk.projects.learning.tools.kanban.persistence.database.DBController;
import com.jellerijk.projects.learning.tools.kanban.persistence.dto.TaskDTO;
import com.jellerijk.projects.learning.tools.kanban.persistence.inserters.TaskInserter;
import com.jellerijk.projects.learning.tools.kanban.persistence.loaders.TaskLoader;

public class TaskControllerImpl implements TaskController {
	private TaskRepository taskRepo;

	private final int boardId;

	public TaskControllerImpl(int boardId) {
		this.boardId = boardId;
		List<Task> tasks = TaskLoader.loadTasksForBoard(boardId).stream().map(dto -> new TaskImpl(dto))
				.collect(Collectors.toCollection(ArrayList::new));
		this.taskRepo = new TaskRepositoryImpl(tasks);
	}

	@Override
	public void createTask(TaskDTO data) {
		try {
			TaskInserter.insert(data);
			int id = DBController.getInstance().getLastInserted("Task");
			addTask(TaskLoader.get(id));
		} catch (SQLException ex) {
			Logger.logError(ex);
		}
	}

	@Override
	public void moveTask(int id, int stageNumber) {
		Task task = taskRepo.getTask(id);
		task.move(stageNumber);
	}

	@Override
	public void updateTask(int id, TaskDTO data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteTask(int id) {
		taskRepo.remove(id);
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

}
