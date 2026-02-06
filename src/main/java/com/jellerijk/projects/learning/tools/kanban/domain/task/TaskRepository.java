package com.jellerijk.projects.learning.tools.kanban.domain.task;

import java.util.List;

public interface TaskRepository {
	/**
	 * Adds a Task to the repository.
	 * 
	 * @param task
	 */
	public void addTask(Task task);

	/**
	 * Gets a Task from the repository
	 * 
	 * @param id
	 * @return
	 */
	public Task getTask(int id);

	/**
	 * Gets all Tasks in the repository.
	 * 
	 * @return
	 */
	public List<Task> getTasks();

	/**
	 * Gets all Tasks in a certain Board and Stage.
	 * 
	 * @param StageNumber
	 * @return
	 */
	public List<Task> getTasksByStage(int boardId, int stageNumber);

}
