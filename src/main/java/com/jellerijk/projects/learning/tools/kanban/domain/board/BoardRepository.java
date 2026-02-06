package com.jellerijk.projects.learning.tools.kanban.domain.board;

import java.util.List;

import com.jellerijk.projects.learning.tools.kanban.persistence.dto.BoardDTO;

public interface BoardRepository {
	
	public void addBoard(Board board);

	public void deleteBoard(int id);

	public Board getBoard(int id);

	public List<Board> getBoards();

	public void setBoards(List<Board> boards);

	public void updateBoard(int id, BoardDTO data);

}
