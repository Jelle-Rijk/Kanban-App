package com.jellerijk.projects.learning.tools.kanban.domain.board;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.jellerijk.projects.learning.tools.kanban.logging.Logger;
import com.jellerijk.projects.learning.tools.kanban.persistence.dto.BoardDTO;

public class BoardRepositoryImpl implements BoardRepository {
	private List<Board> boards;

	public BoardRepositoryImpl() {
		setBoards(new ArrayList<Board>());
	};

	@Override
	public void addBoard(BoardDTO dto) {
		// TODO: insert board into database.
		Logger.log("Trying to make a Board from dto.");
		Logger.log(String.format("BoardDTO data: id - %d, name - %s, description - %s", dto.id(), dto.name(),
				dto.description()));
		Board board = new BoardImpl(dto.id(), dto.name(), dto.description());
		boards.add(board);
		Logger.log(String.format("Added a %s \"%s\" with id %d.", board.getClass().getSimpleName(), board.getName(),
				board.getId()));
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
