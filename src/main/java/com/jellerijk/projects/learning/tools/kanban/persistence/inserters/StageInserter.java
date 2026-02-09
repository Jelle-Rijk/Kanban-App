package com.jellerijk.projects.learning.tools.kanban.persistence.inserters;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.jellerijk.projects.learning.tools.kanban.logging.Logger;
import com.jellerijk.projects.learning.tools.kanban.persistence.database.DBController;
import com.jellerijk.projects.learning.tools.kanban.persistence.dto.StageDTO;

public abstract class StageInserter {
	private final static String SQL = "INSERT INTO Stage(Number, BoardId, Title, Description, TaskLimit) VALUES (?,?,?,?,?)";

	public static void insert(StageDTO stage) throws SQLException {
		PreparedStatement pstmt = DBController.getInstance().prepareStatement(SQL);
		executePreparedStatement(pstmt, stage);
	}

	public static void insert(List<StageDTO> stages) throws SQLException {
		PreparedStatement pstmt = DBController.getInstance().prepareStatement(SQL);
		for (StageDTO stage : stages)
			executePreparedStatement(pstmt, stage);
	}

	private static void executePreparedStatement(PreparedStatement pstmt, StageDTO stage) throws SQLException {
		pstmt.setInt(1, stage.number());
		pstmt.setInt(2, stage.boardId());
		pstmt.setString(3, stage.title());
		pstmt.setString(4, stage.description());
		pstmt.setInt(5, stage.limit());
		pstmt.execute();
	}
}
