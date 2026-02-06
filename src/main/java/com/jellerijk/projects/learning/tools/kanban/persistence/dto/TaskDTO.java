package com.jellerijk.projects.learning.tools.kanban.persistence.dto;

import com.jellerijk.projects.learning.tools.kanban.domain.task.Task;

public record TaskDTO(int id, String description, int boardId, int stageNumber, boolean completed) {
	public static TaskDTO convert(Task task) {
		return new TaskDTO(task.getId(), task.getDescription(), task.getBoardId(), task.getStageNumber(),
				task.isCompleted());
	}

}
