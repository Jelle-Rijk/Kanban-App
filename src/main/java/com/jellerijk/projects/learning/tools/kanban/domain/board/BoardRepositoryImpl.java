package com.jellerijk.projects.learning.tools.kanban.domain.board;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.jellerijk.projects.learning.tools.kanban.logging.Logger;
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
			int id = mapper.insert(board);
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
			mapper.delete(board);
			boards.remove(board);
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
		// TODO: reimplement board updates.
		throw new UnsupportedOperationException();
	}

}
