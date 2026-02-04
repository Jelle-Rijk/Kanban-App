package com.jellerijk.projects.learning.tools.kanban.domain.board;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.jellerijk.projects.learning.tools.kanban.logging.Logger;
import com.jellerijk.projects.learning.tools.kanban.persistence.database.DBController;
import com.jellerijk.projects.learning.tools.kanban.persistence.dto.BoardDTO;

public class BoardRepositoryImpl implements BoardRepository {
	private List<Board> boards;

	public BoardRepositoryImpl() {
		setBoards(retrieveBoardsFromDatabase());

	};

	private List<Board> retrieveBoardsFromDatabase() {
		List<Board> boards = new ArrayList<Board>();
		String sql = String.format("SELECT * FROM Board;");
		ResultSet dbResults = DBController.getInstance().query(sql);
		try {
			while (dbResults.next()) {
				int id = dbResults.getInt("boardId");
				String name = dbResults.getString("Name");
				String description = dbResults.getString("Description");
				Board board = new BoardImpl(id, name, description);
				boards.add(board);
			}
		} catch (SQLException e) {
			// TODO: Handle exception
			e.printStackTrace();
		}
		return boards;
	}

	@Override
	public void addBoard(BoardDTO dto) {
		// TODO: insert board into database and retrieve its id (incoming DTO has an id
		// of -1)
		DBController database = DBController.getInstance();

		String sql = String.format("INSERT INTO Board (Name, Description) VALUES (\"%s\", \"%s\");", dto.name(),
				dto.description());
		database.update(sql);

		ResultSet lastInsertedId = database.query("SELECT seq FROM sqlite_sequence WHERE name=\"Board\";");
		try {
			int id = lastInsertedId.getInt("seq");
			Board board = new BoardImpl(id, dto.name(), dto.description());
			boards.add(board);
			Logger.log(String.format("Added a %s \"%s\" with id %d.", board.getClass().getSimpleName(), board.getName(),
					board.getId()));
		} catch (SQLException ex) {
			Logger.logError("Could not find last inserted id.");
		}
	}

	@Override
	public void deleteBoard(int id) {
		try {
			Board board = getBoard(id);
			boards.remove(board);
			// TODO: delete board from database.
			Logger.log(String.format("Board %d was removed.", id));
		} catch (NoSuchElementException nse) {
			Logger.logError(String.format("Board %d was not found. It cannot be deleted.", id));
		}
	}

	@Override
	public Board getBoard(int id) {
		return boards.stream().filter(board -> board.getId() == id).findAny().orElseThrow();
	}

	@Override
	public List<Board> getBoards() {
		return boards;
	}

	@Override
	public void setBoards(List<Board> boards) {
		this.boards = boards;
	}

	@Override
	public void updateBoard(int id, BoardDTO data) {
		Board board = getBoard(id);
		board.setName(data.name());
		board.setDescription(data.description());
		// TODO: update database

	}

}
