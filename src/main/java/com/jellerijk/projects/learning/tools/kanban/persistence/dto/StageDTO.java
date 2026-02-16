package com.jellerijk.projects.learning.tools.kanban.persistence.dto;

import com.jellerijk.projects.learning.tools.kanban.domain.Stage;

public record StageDTO(String id, String name, int limit) {
	public static StageDTO convert(Stage stage) {
		return new StageDTO(stage.getId(), stage.getName(), stage.getLimit());
	};

	public static StageDTO create(String id, String name, int limit) {
		return new StageDTO(id, name, limit);
	};
}
