package com.jellerijk.projects.learning.tools.kanban.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import com.jellerijk.projects.learning.tools.kanban.exceptions.DatabaseInsertException;
import com.jellerijk.projects.learning.tools.kanban.exceptions.DatabaseReadException;
import com.jellerijk.projects.learning.tools.kanban.exceptions.DatabaseUpdateException;
import com.jellerijk.projects.learning.tools.kanban.persistence.mappers.BoardMapper;

public class BoardRepository implements Repository<Board> {
	private BoardMapper mapper;
	private Map<String, Board> boards;

	public BoardRepository() {
		mapper = new BoardMapper();
		boards = initBoards();
	}

	private Map<String, Board> initBoards() {
		Map<String, Board> map = new HashMap<String, Board>();
		List<Board> boards = mapper.getAll();
		for (Board board : boards) {
			map.put(board.getId(), board);
		}
		return map;
	}

	@Override
	public void add(Board board) throws DatabaseInsertException {
		mapper.insert(board);
		boards.put(board.getId(), board);
	}
	

	@Override
	public List<Board> getAsList() throws DatabaseReadException {
		return new ArrayList<Board>(boards.values());
	}

	@Override
	public Board getById(String id) throws NoSuchElementException {
		Board board = boards.get(id);
		if (board == null)
			throw new NoSuchElementException();
		return boards.get(id);
	}

	@Override
	public void update(Board board) throws DatabaseUpdateException {
		mapper.update(board);
		boards.put(board.getId(), board);
	}

	@Override
	public void delete(String id) throws DatabaseUpdateException {
		Board board = getById(id);
		mapper.delete(board);
	}


}
