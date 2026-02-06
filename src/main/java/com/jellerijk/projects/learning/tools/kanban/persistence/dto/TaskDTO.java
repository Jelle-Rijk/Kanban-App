package com.jellerijk.projects.learning.tools.kanban.persistence.dto;

import com.jellerijk.projects.learning.tools.kanban.domain.task.Task;

public record TaskDTO(int id, String description, int boardId, int stageNumber, boolean completed) {
	public static TaskDTO convert(Task task) {
		return new TaskDTO(task.getId(), task.getDescription(), task.getBoardId(), task.getStageNumber(),
				task.isCompleted());
	}

	public static TaskDTO create(int id, String description, int board, int stage, boolean completed) {
		return new TaskDTO(id, description, board, stage, completed);
	}
}
