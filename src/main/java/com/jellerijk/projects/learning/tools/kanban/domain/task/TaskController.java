package com.jellerijk.projects.learning.tools.kanban.domain.task;

import java.util.List;

import com.jellerijk.projects.learning.tools.kanban.persistence.dto.TaskDTO;
import com.jellerijk.projects.learning.tools.kanban.utils.Publisher;
import com.jellerijk.projects.learning.tools.kanban.utils.Subscriber;

public interface TaskController extends Publisher {
	public static TaskController getInstance() {
		return TaskControllerImpl.getInstance();
	}

	/**
	 * Creates a new task.
	 * 
	 * @param data
	 * @return The id of the newly created task.
	 */
	public int createTask(String description, int board, int stageNumber);

	/**
	 * Moves the task from its current stage to stageNumber.
	 * 
	 * @param id
	 * @param stageNumber
	 */
	public void moveTask(int id, int stageNumber);

	/**
	 * Deletes a task.
	 * 
	 * @param id
	 */
	public void deleteTask(int id);

	/**
	 * Completes the task.
	 * 
	 * @param id
	 */
	public void completeTask(int id);

	/**
	 * Gets the task by its id.
	 * 
	 * @param id
	 * @return
	 */
	public TaskDTO getTask(int id);

	/**
	 * Gets all tasks for a given StageNumber.
	 * 
	 * @param stageNumber
	 * @return
	 */
	public List<TaskDTO> getTasksForStage(int stageNumber, int boardId);

	/**
	 * Subscribes sub to the specified task.
	 * 
	 * @param sub
	 * @param id
	 */
	public void subscribeToTask(Subscriber sub, int id);
}
