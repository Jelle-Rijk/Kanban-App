package com.jellerijk.projects.learning.tools.kanban.domain.task;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.jellerijk.projects.learning.tools.kanban.exceptions.DatabaseInsertException;
import com.jellerijk.projects.learning.tools.kanban.logging.Logger;
import com.jellerijk.projects.learning.tools.kanban.persistence.mappers.TaskMapper;

public class TaskRepositoryImpl implements TaskRepository {
	private List<Task> tasks;
	private TaskMapper mapper;

	public TaskRepositoryImpl() {
		mapper = new TaskMapper();
		tasks = (ArrayList<Task>) mapper.getAll();
	}

	@Override
	public int addTask(Task task) {
		try {
			int id = mapper.insert(task);
			task.setId(id);
			tasks.add(task);
			return id;
		} catch (DatabaseInsertException ex) {
			throw new DatabaseInsertException("Could not add task", ex);
		}
	}

	@Override
	public Task getTask(int id) {
		return tasks.stream().filter(task -> task.getId() == id).findFirst().orElseThrow();
	}

	@Override
	public List<Task> getTasks() {
		return tasks;
	}

	@Override
	public List<Task> getTasksByStage(int boardId, int stageNumber) {
		return tasks.stream().filter(task -> task.getBoardId() == boardId && task.getStageNumber() == stageNumber)
				.collect(Collectors.toCollection(ArrayList<Task>::new));
	}

	@Override
	public void remove(int id) {
		try {
			Task task = getTask(id);
			mapper.delete(task);
			tasks.remove(task);
		} catch (Exception ex) {
			Logger.logError(String.format("Something went wrong while removing task.", id));
			Logger.logError(ex);
		}
	}

}
