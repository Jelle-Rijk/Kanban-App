package com.jellerijk.projects.learning.tools.kanban.domain.board;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import com.jellerijk.projects.learning.tools.kanban.logging.Logger;
import com.jellerijk.projects.learning.tools.kanban.persistence.database.DBController;
import com.jellerijk.projects.learning.tools.kanban.persistence.dto.BoardDTO;
import com.jellerijk.projects.learning.tools.kanban.persistence.loaders.BoardLoader;

public class BoardRepositoryImpl implements BoardRepository {
	private List<Board> boards;

	public BoardRepositoryImpl() {
		Collection<BoardDTO> data = BoardLoader.loadAll();
		List<Board> convertedData = data.stream()
				.map(board -> new BoardImpl(board.id(), board.name(), board.description()))
				.collect(Collectors.toCollection(ArrayList::new));
		setBoards(convertedData);
	};

	@Override
	public void addBoard(Board board) {
		boards.add(board);
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
