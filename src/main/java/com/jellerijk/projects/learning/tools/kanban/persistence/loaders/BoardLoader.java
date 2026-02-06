package com.jellerijk.projects.learning.tools.kanban.persistence.loaders;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.jellerijk.projects.learning.tools.kanban.logging.Logger;
import com.jellerijk.projects.learning.tools.kanban.persistence.database.DBController;
import com.jellerijk.projects.learning.tools.kanban.persistence.dto.BoardDTO;

public abstract class BoardLoader {
	public static Collection<BoardDTO> loadAll() {
		ResultSet results = DBController.getInstance().query("SELECT * FROM Board");
		return convertResultSet(results);
	}

	private static Collection<BoardDTO> convertResultSet(ResultSet results) {
		Collection<BoardDTO> boards = new ArrayList<BoardDTO>();
		try {
			while (results.next()) {
				int boardId = results.getInt("BoardId");
				String name = results.getString("Name");
				String description = results.getString("Description");

				BoardDTO board = BoardDTO.create(boardId, name, description);
				boards.add(board);
			}
		} catch (SQLException e) {
			Logger.logError("Something went wrong while trying to convert ResultSet to Boards.");
			e.printStackTrace();
		}
		return boards;
	}
}
