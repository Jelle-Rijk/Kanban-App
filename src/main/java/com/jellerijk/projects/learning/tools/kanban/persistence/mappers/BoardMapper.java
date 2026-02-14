package com.jellerijk.projects.learning.tools.kanban.persistence.mappers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.jellerijk.projects.learning.tools.kanban.domain.board.Board;
import com.jellerijk.projects.learning.tools.kanban.domain.board.BoardImpl;
import com.jellerijk.projects.learning.tools.kanban.exceptions.DatabaseInsertException;
import com.jellerijk.projects.learning.tools.kanban.logging.Logger;
import com.jellerijk.projects.learning.tools.kanban.persistence.database.DBController;

public class BoardMapper implements Mapper<Board> {
	private Connection conn;

	private final static String TABLE = "Board";
	private final static String COL_ID = "BoardId";
	private final static String COL_NAME = "Name";
	private final static String COL_DESCRIPTION = "Description";

	private final static String INSERT_BOARD = String.format("INSERT INTO %s (%s, %s) VALUES (?,?)", TABLE, COL_NAME,
			COL_DESCRIPTION);
	private final static String QUERY_ALL = String.format("SELECT * FROM %s", TABLE);

	public BoardMapper() {
		conn = DBController.getInstance().getConnection();
	}

//	CREATE
	@Override
	public void insert(Board board) throws DatabaseInsertException {
		try (PreparedStatement query = conn.prepareStatement(INSERT_BOARD)) {
			query.setString(1, board.getName());
			query.setString(2, board.getDescription());
			query.execute();
			Logger.log("Inserted a Board into the database.");
		} catch (SQLException e) {
			Logger.logError("Something went wrong while inserting a Board");
			throw new DatabaseInsertException();
		}
	}

//	READ
	@Override
	public Collection<Board> getAll() {
		Collection<Board> boards = new ArrayList<Board>();
		try (PreparedStatement query = conn.prepareStatement(QUERY_ALL)) {
			ResultSet results = query.executeQuery();
			boards = mapResults(results);
			Logger.log(String.format("Loaded %d Board records.", boards.size()));
		} catch (SQLException e) {
			Logger.logError("Something went wrong while retrieving all Boards from database.");
			Logger.logError(e);
			e.printStackTrace();
		}
		return boards;
	}

	/**
	 * Gets the last inserted Board's id.
	 * 
	 * @return Id of the last inserted Board.
	 * @throws SQLException - If the ID was not found.
	 */
	public int getLastInsertedId() throws SQLException {
		try (PreparedStatement query = conn.prepareStatement("SELECT seq FROM sqlite_sequence WHERE name = ?")) {
			query.setString(1, TABLE);
			ResultSet results = query.executeQuery();
			if (!results.next())
				throw new SQLException();
			return results.getInt("seq");
		} catch (SQLException e) {
			Logger.logError("Encountered an exception while retrieving last inserted Board Id.");
			throw new SQLException();
		}
	}

	// HELPER METHODS
	private Collection<Board> mapResults(ResultSet results) throws SQLException {
		Collection<Board> boards = new ArrayList<Board>();
		while (results.next()) {
			int id = results.getInt(COL_ID);
			String name = results.getString(COL_NAME);
			String description = results.getString(COL_DESCRIPTION);

			Board board = new BoardImpl(id, name, description);
			boards.add(board);
		}
		return boards;
	}

}
