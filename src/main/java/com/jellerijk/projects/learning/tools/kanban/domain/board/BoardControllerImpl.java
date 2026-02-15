package com.jellerijk.projects.learning.tools.kanban.domain.board;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.jellerijk.projects.learning.tools.kanban.persistence.dto.BoardDTO;
import com.jellerijk.projects.learning.tools.kanban.utils.PublishedMessageType;
import com.jellerijk.projects.learning.tools.kanban.utils.Subscriber;

public class BoardControllerImpl implements BoardController {
	private static BoardController instance;

	private final BoardRepository boardRepo;
	private final List<Subscriber> subscribers;

	private BoardControllerImpl() {
		boardRepo = new BoardRepositoryImpl();
		subscribers = new ArrayList<Subscriber>();
	}

	public static BoardController getInstance() {
		if (instance == null)
			instance = new BoardControllerImpl();
		return instance;
	}

	@Override
	public void createBoard(String name, String description) {
		Board newBoard = new BoardImpl(name, description);
		boardRepo.addBoard(newBoard);
		notifySubs(PublishedMessageType.REPO_UPDATE);
	}

	@Override
	public BoardDTO getBoard(int id) {
		Board board = boardRepo.getBoard(id);
		return BoardDTO.convert(board);
	}

	@Override
	public void updateBoard(int id, BoardDTO updatedBoard) {
		boardRepo.updateBoard(id, updatedBoard);
	}

	@Override
	public void deleteBoard(int id) {
		boardRepo.deleteBoard(id);
		notifySubs(PublishedMessageType.REPO_UPDATE);
	}

	@Override
	public List<BoardDTO> getBoards() {
		List<Board> boards = boardRepo.getBoards();
		List<BoardDTO> dtos = new ArrayList<BoardDTO>();
		boards.forEach(board -> dtos.add(BoardDTO.convert(board)));
		return dtos;
	}

	@Override
	public void subscribe(Subscriber sub, int boardId) {
		Board board = boardRepo.getBoard(boardId);
		board.subscribe(sub);
	}

	@Override
	public Collection<Subscriber> getSubscribers() {
		return subscribers;
	}

}
