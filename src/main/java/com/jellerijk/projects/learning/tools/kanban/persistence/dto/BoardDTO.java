package com.jellerijk.projects.learning.tools.kanban.persistence.dto;

import com.jellerijk.projects.learning.tools.kanban.domain.board.Board;

public record BoardDTO(int id, String name, String description) {
	public static BoardDTO convert(Board board) {
		return new BoardDTO(board.getId(), board.getName(), board.getDescription());
	};

	public static BoardDTO create(int id, String name, String description) {
		return new BoardDTO(id, name, description);
	}
}
