package com.jellerijk.projects.learning.tools.kanban.persistence.dto;

import com.jellerijk.projects.learning.tools.kanban.domain.stage.Stage;

public record StageDTO(int number, String title, int boardId, String description, int limit) {
	public static StageDTO convert(Stage stage) {
		return new StageDTO(stage.getNumber(), stage.getTitle(), stage.getBoardId(), stage.getDescription(),
				stage.getLimit());
	};

	public static StageDTO create(int number, String title, int boardId, String description, int limit) {
		return new StageDTO(number, title, boardId, description, limit);
	};
}
