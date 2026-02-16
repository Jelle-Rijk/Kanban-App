package com.jellerijk.projects.learning.tools.kanban.persistence.dto;

import com.jellerijk.projects.learning.tools.kanban.domain.Task;

public record TaskDTO(String id, String description, String details) {
	public static TaskDTO convert(Task task) {
		return new TaskDTO(task.getId(), task.getDescription(), task.getDetails());
	}

	public static TaskDTO create(String id, String description, String details) {
		return new TaskDTO(id, description, details);
	}
}
