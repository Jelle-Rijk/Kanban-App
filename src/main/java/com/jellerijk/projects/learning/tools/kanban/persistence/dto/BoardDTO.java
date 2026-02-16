package com.jellerijk.projects.learning.tools.kanban.persistence.dto;

import com.jellerijk.projects.learning.tools.kanban.domain.Board;

public record BoardDTO(String id, String title, String description) {
	public static BoardDTO convert(Board board) {
		return new BoardDTO(board.getId(), board.getTitle(), board.getDescription());
	};

	public static BoardDTO create(String id, String title, String description) {
		return new BoardDTO(id, title, description);
	}
}
