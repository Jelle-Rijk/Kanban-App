package com.jellerijk.projects.learning.tools.kanban.persistence.inserters;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

import com.jellerijk.projects.learning.tools.kanban.persistence.database.DBController;
import com.jellerijk.projects.learning.tools.kanban.persistence.dto.BoardDTO;

public abstract class BoardInserter {
	private static final String SQL = "INSERT INTO Board (Name, Description) VALUES (?,?)";

	public static void insert(BoardDTO board) throws SQLException {
		PreparedStatement pstmt = DBController.getInstance().prepareStatement(SQL);
		executePreparedStatement(pstmt, board);
	}

	public static void insert(Collection<BoardDTO> boards) throws SQLException {
		PreparedStatement pstmt = DBController.getInstance().prepareStatement(SQL);
		for (BoardDTO board : boards)
			executePreparedStatement(pstmt, board);
	}

	private static void executePreparedStatement(PreparedStatement pstmt, BoardDTO board) throws SQLException {
		pstmt.setString(1, board.name());
		pstmt.setString(2, board.description());
		pstmt.execute();
	};
}
