package com.jellerijk.projects.learning.tools.kanban.domain.board;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.jellerijk.projects.learning.tools.kanban.logging.Logger;
import com.jellerijk.projects.learning.tools.kanban.persistence.database.DBController;
import com.jellerijk.projects.learning.tools.kanban.persistence.dto.BoardDTO;
import com.jellerijk.projects.learning.tools.kanban.persistence.inserters.BoardInserter;

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
		try {
			BoardInserter.insert(dto);
			Logger.log("Inserted a new Board into the database.");
			int id = DBController.getInstance().getLastInserted("Board");
			Board board = new BoardImpl(id, dto.name(), dto.description());
			boards.add(board);
		} catch (SQLException ex) {
			Logger.logError(
					String.format("Something went wrong while inserting a new Board into the database.%n%s - %s",
							ex.getClass().getSimpleName(), ex.getMessage()));
		}
	}

	@Override
	public void deleteBoard(int id) {
		try {
			Board board = getBoard(id);
			boards.remove(board);
			String sql = String.format("DELETE FROM Board WHERE BoardId=%d", id);
			DBController.getInstance().update(sql);
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
		String sql = String.format("UPDATE Board SET name=\"%s\", description=\"%s\" WHERE BoardId=%d", data.name(),
				data.description(), id);
		DBController.getInstance().update(sql);
		Board board = getBoard(id);
		board.updateData(data);
	}

}
