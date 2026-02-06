package com.jellerijk.projects.learning.tools.kanban.domain.task;

import com.jellerijk.projects.learning.tools.kanban.persistence.dto.TaskDTO;
import com.jellerijk.projects.learning.tools.kanban.utils.Publisher;

public interface Task extends Publisher {
	/**
	 * Returns the Task's id.
	 * 
	 * @return
	 */
	public int getId();

	/**
	 * Returns the Task's description.
	 * 
	 * @return
	 */
	public String getDescription();

	/**
	 * Returns the index of the Stage within the board that the Task belongs to.
	 * 
	 * @return
	 */
	public int getStageNumber();

	/**
	 * Returns the id of the Board that this Task belongs to.
	 * 
	 * @return
	 */
	public int getBoardId();

	/**
	 * Returns True if the Task is marked as completed.
	 * 
	 * @return
	 */
	public boolean isCompleted();

	/**
	 * Moves this task to a different stage.
	 * 
	 * @param stageNumber - Number of the stage to move this task to.
	 */
	public void move(int stageNumber);

	/**
	 * Updates the Task based on the inserted TaskDTO.
	 * 
	 * @param data
	 */
	public void updateData(TaskDTO data);

	/**
	 * Marks the task as completed.
	 */
	public void complete();
}
