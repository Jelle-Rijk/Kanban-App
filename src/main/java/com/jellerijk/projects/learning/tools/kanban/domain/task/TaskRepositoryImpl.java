package com.jellerijk.projects.learning.tools.kanban.domain.task;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskRepositoryImpl implements TaskRepository {
	private List<Task> tasks;

	public TaskRepositoryImpl() {
	}

	@Override
	public void addTask(Task task) {
		tasks.add(task);
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



}
