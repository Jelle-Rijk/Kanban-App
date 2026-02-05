package com.jellerijk.projects.learning.tools.kanban.domain.task;

import com.jellerijk.projects.learning.tools.kanban.persistence.dto.TaskDTO;

public interface TaskController {
	/**
	 * Creates a new task.
	 * 
	 * @param data
	 */
	public void createTask(TaskDTO data);

	public void updateTask(TaskDTO data);
}
