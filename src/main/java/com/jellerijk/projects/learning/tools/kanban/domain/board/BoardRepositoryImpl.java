package com.jellerijk.projects.learning.tools.kanban.domain.board;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.jellerijk.projects.learning.tools.kanban.exceptions.DatabaseInsertException;
import com.jellerijk.projects.learning.tools.kanban.logging.Logger;
import com.jellerijk.projects.learning.tools.kanban.persistence.database.DBController;
import com.jellerijk.projects.learning.tools.kanban.persistence.dto.BoardDTO;
import com.jellerijk.projects.learning.tools.kanban.persistence.mappers.BoardMapper;

public class BoardRepositoryImpl implements BoardRepository {
	private List<Board> boards;
	private BoardMapper mapper;

	public BoardRepositoryImpl() {
		mapper = new BoardMapper();
		setBoards((ArrayList<Board>) mapper.getAll());
	};

	@Override
	public void addBoard(Board board) {
		try {
			mapper.insert(board);
			int id = mapper.getLastInsertedId();
			board.setId(id);
			boards.add(board);
		} catch (Exception ex) {
			Logger.logError(ex);
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
