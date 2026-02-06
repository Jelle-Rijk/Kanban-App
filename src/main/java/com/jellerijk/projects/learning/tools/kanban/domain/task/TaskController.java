package com.jellerijk.projects.learning.tools.kanban.domain.task;

import java.util.List;

import com.jellerijk.projects.learning.tools.kanban.persistence.dto.TaskDTO;

public interface TaskController {
	/**
	 * Creates a new task.
	 * 
	 * @param data
	 */
	public void createTask(TaskDTO data);

	/**
	 * Moves the task from its current stage to stageNumber.
	 * @param id
	 * @param stageNumber
	 */
	public void moveTask(int id, int stageNumber);

	/**
	 * Updates a task with new data.
	 * @param id
	 * @param data
	 */
	public void updateTask(int id, TaskDTO data);

	/**
	 * Deletes a task.
	 * @param id
	 */
	public void deleteTask(int id);

	/**
	 * Completes the task.
	 * @param id
	 */
	public void completeTask(int id);

	/**
	 * Gets the task by its id.
	 * @param id
	 * @return
	 */
	public TaskDTO getTask(int id);

	/**
	 * Gets all tasks for a given BoardId and StageNumber.
	 * @param boardId
	 * @param stageNumber
	 * @return
	 */
	public List<TaskDTO> getTasks(int boardId, int stageNumber);
}
