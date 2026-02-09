package com.jellerijk.projects.learning.tools.kanban.persistence.loaders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.jellerijk.projects.learning.tools.kanban.logging.Logger;
import com.jellerijk.projects.learning.tools.kanban.persistence.database.DBController;
import com.jellerijk.projects.learning.tools.kanban.persistence.dto.StageDTO;

public abstract class StageLoader {
	public static Collection<StageDTO> loadByBoard(int boardId) throws SQLException {
		PreparedStatement pstmt = DBController.getInstance().prepareStatement("SELECT * FROM Stage WHERE BoardId = ?");
		pstmt.setInt(1, boardId);
		ResultSet results = pstmt.executeQuery();
		return convertResultSet(results);
	}

	public static Collection<StageDTO> loadAll() {
		ResultSet results = DBController.getInstance().query("SELECT * FROM Stage");
		return convertResultSet(results);
	}

	private static Collection<StageDTO> convertResultSet(ResultSet results) {
		Collection<StageDTO> stages = new ArrayList<StageDTO>();
		try {
			while (results.next()) {
				int board = results.getInt("BoardId");
				int number = results.getInt("Number");
				String title = results.getString("Title");
				String description = results.getString("Description");
				int limit = results.getInt("TaskLimit");

				StageDTO stage = StageDTO.create(number, title, board, description, limit);
				stages.add(stage);
			}
		} catch (SQLException e) {
			Logger.logError("Something went wrong while trying to convert ResultSet to Boards.");
			e.printStackTrace();
		}
		return stages;
	}
}
