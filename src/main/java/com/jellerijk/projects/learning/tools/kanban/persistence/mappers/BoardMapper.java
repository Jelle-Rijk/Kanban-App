package com.jellerijk.projects.learning.tools.kanban.persistence.mappers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import com.jellerijk.projects.learning.tools.kanban.domain.board.Board;
import com.jellerijk.projects.learning.tools.kanban.domain.board.BoardImpl;
import com.jellerijk.projects.learning.tools.kanban.exceptions.DatabaseInsertException;
import com.jellerijk.projects.learning.tools.kanban.exceptions.DatabaseReadException;
import com.jellerijk.projects.learning.tools.kanban.logging.Logger;
import com.jellerijk.projects.learning.tools.kanban.persistence.database.DBController;

public class BoardMapper implements Mapper<Board> {
	private DBController dbc;

	private static final String TABLE = "Board";
	private static final String COL_ID = "BoardId";
	private static final String COL_NAME = "Name";
	private static final String COL_DESCRIPTION = "Description";

	private static final String INSERT_BOARD = String.format("INSERT INTO %s (%s, %s) VALUES (?,?)", TABLE, COL_NAME,
			COL_DESCRIPTION);

	private static final String QUERY_ALL = String.format("SELECT * FROM %s", TABLE);

	private static final String DELETE_BOARD = String.format("DELETE FROM %s WHERE %s = ?", TABLE, COL_ID);

	public BoardMapper() {
		dbc = DBController.getInstance();
	}

//	CREATE
	@Override
	public int insert(Board board) throws DatabaseInsertException {
		int lastInsertedId = -1;
		try (Connection conn = dbc.getConnection();
				PreparedStatement query = conn.prepareStatement(INSERT_BOARD, Statement.RETURN_GENERATED_KEYS)) {
			query.setString(1, board.getName());
			query.setString(2, board.getDescription());
			query.executeUpdate();
			ResultSet keys = query.getGeneratedKeys();
			if (keys.next())
				lastInsertedId = keys.getInt(1);
			Logger.log("Inserted a Board into the database.");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseInsertException("Failed to insert Board", e);
		}
		return lastInsertedId;
	}

//	READ
	@Override
	public Collection<Board> getAll() {
		Collection<Board> boards = new ArrayList<>();
		try (Connection conn = dbc.getConnection(); PreparedStatement query = conn.prepareStatement(QUERY_ALL)) {
			ResultSet results = query.executeQuery();
			boards = mapResults(results);
			Logger.log(String.format("Loaded %d Board records.", boards.size()));
			return boards;
		} catch (SQLException e) {
			Logger.logError("Something went wrong while retrieving all Boards from database.");
			Logger.logError(e);
		}
		throw new DatabaseReadException();
	}

	// HELPER METHODS
	private Collection<Board> mapResults(ResultSet results) throws SQLException {
		Collection<Board> boards = new ArrayList<>();
		while (results.next()) {
			int id = results.getInt(COL_ID);
			String name = results.getString(COL_NAME);
			String description = results.getString(COL_DESCRIPTION);

			Board board = new BoardImpl(id, name, description);
			boards.add(board);
		}
		return boards;
	}

	// DELETE
	@Override
	public void delete(Board board) {
		try (Connection conn = dbc.getConnection(); PreparedStatement query = conn.prepareStatement(DELETE_BOARD)) {
			query.setInt(1, board.getId());
			int rows = query.executeUpdate();
			String log = rows == 0 ? String.format("No board found with id %d", board.getId())
					: String.format("Removed board %d from database", board.getId());
			Logger.log(log);
		} catch (SQLException e) {
			Logger.logError("Something went wrong while deleting Board from database.");
			Logger.logError(e);
		}
	}

}
