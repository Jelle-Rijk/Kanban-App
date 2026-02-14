package com.jellerijk.projects.learning.tools.kanban.persistence.mappers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.jellerijk.projects.learning.tools.kanban.domain.stage.Stage;
import com.jellerijk.projects.learning.tools.kanban.domain.stage.StageImpl;
import com.jellerijk.projects.learning.tools.kanban.exceptions.DatabaseInsertException;
import com.jellerijk.projects.learning.tools.kanban.exceptions.DatabaseReadException;
import com.jellerijk.projects.learning.tools.kanban.logging.Logger;
import com.jellerijk.projects.learning.tools.kanban.persistence.database.DBController;

public class StageMapper implements Mapper<Stage> {
	private DBController dbc;

	private static final String TABLE = "Stage";
	private static final String COL_NUMBER = "Number";
	private static final String COL_BOARD = "BoardId";
	private static final String COL_TITLE = "Title";
	private static final String COL_DESCRIPTION = "Description";
	private static final String COL_TASKLIMIT = "TaskLimit";

	private static final String INSERT_STAGE = String.format("INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (?,?,?,?,?)",
			TABLE, COL_NUMBER, COL_BOARD, COL_TITLE, COL_DESCRIPTION, COL_TASKLIMIT);
	private static final String QUERY_ALL = String.format("SELECT * FROM %s", TABLE);

	private static final String DELETE_STAGE = String.format("DELETE FROM %s WHERE %s = ? AND %s = ?", TABLE,
			COL_NUMBER, COL_BOARD);

	@Override
	public int insert(Stage stage) {
		try (Connection conn = dbc.getConnection(); PreparedStatement query = conn.prepareStatement(INSERT_STAGE)) {
			query.setInt(1, stage.getNumber());
			query.setInt(2, stage.getBoardId());
			query.setString(3, stage.getTitle());
			query.setString(4, stage.getDescription());
			query.setInt(5, stage.getLimit());
			query.executeUpdate();
			Logger.log("Inserted a Stage into the database.");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseInsertException("Failed to insert Stage", e);
		}
		return -1;
	}

	@Override
	public Collection<Stage> getAll() {
		Collection<Stage> stages = new ArrayList<>();
		try (Connection conn = dbc.getConnection(); PreparedStatement query = conn.prepareStatement(QUERY_ALL)) {
			ResultSet results = query.executeQuery();
			stages = mapResults(results);
			Logger.log(String.format("Loaded %d Stage records.", stages.size()));
			return stages;
		} catch (SQLException e) {
			Logger.logError("Something went wrong while retrieving all Stages from database.");
			Logger.logError(e);
		}
		throw new DatabaseReadException();
	}

	private Collection<Stage> mapResults(ResultSet results) throws SQLException {
		List<Stage> stages = new ArrayList<>();
		while (results.next()) {
			int number = results.getInt(COL_NUMBER);
			int board = results.getInt(COL_BOARD);
			String title = results.getString(COL_TITLE);
			String description = results.getString(COL_DESCRIPTION);
			int limit = results.getInt(COL_TASKLIMIT);

			Stage stage = new StageImpl(number, board, title, description, limit);
			stages.add(stage);
		}
		return stages;
	}

	@Override
	public void delete(Stage stage) {
		try (Connection conn = dbc.getConnection(); PreparedStatement query = conn.prepareStatement(DELETE_STAGE)) {
			query.setInt(1, stage.getNumber());
			query.setInt(2, stage.getBoardId());
			int rows = query.executeUpdate();
			String log = rows == 0
					? String.format("No stage found with number %d on board %d", stage.getNumber(), stage.getBoardId())
					: String.format("Removed stage %d from database", stage.getNumber());
			Logger.log(log);
		} catch (SQLException e) {
			Logger.logError("Something went wrong while deleting Stage from database.");
			Logger.logError(e);
		}
	}

}
