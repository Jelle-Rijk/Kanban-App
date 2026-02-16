package com.jellerijk.projects.learning.tools.kanban.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.jellerijk.projects.learning.tools.kanban.logging.Logger;
import com.jellerijk.projects.learning.tools.kanban.persistence.dto.BoardDTO;

public class DomainController {

	private static DomainController instance;

	private BoardRepository boardRepo;
	private Board selectedBoard;

	private DomainController() {
		boardRepo = new BoardRepository();
	}

	public static DomainController getInstance() {
		if (instance == null)
			instance = new DomainController();
		return instance;
	}

	/**
	 * Creates a new Board.
	 * 
	 * @param title       - Board's title
	 * @param description - Board's description
	 * @return String - Board's id
	 */
	public String createBoard(String title, String description) {
		String id = UUID.randomUUID().toString();
		Board board = new Board(id, title, description);
		Logger.logDebug(String.format("Generated id: %s", board.getId()));
		boardRepo.add(board);
		return id;
	}

	/**
	 * Deletes the Board with the supplied id.
	 * 
	 * @param id of the Board to be deleted.
	 */
	public void deleteBoard(String id) {
		boardRepo.delete(id);
	}

	/**
	 * Returns a list of all BoardDTOs in the DomainController's BoardRepository.
	 * 
	 * @return An ArrayList containing a BoardDTO for every Board in the repository.
	 */
	public List<BoardDTO> getAllBoards() {
		return boardRepo.getAsList().stream().map(board -> BoardDTO.convert(board))
				.collect(Collectors.toCollection(ArrayList::new));
	}

	/**
	 * Returns a DTO containing the Board's data.
	 * 
	 * @param id - Id for the requested Board.
	 * @return BoardDTO containing the requested Board's data.
	 */
	public BoardDTO getBoard(String id) {
		return BoardDTO.convert(boardRepo.getById(id));
	}

	/**
	 * Returns the selected Board as a DTO.
	 * 
	 * @return BoardDTO containing selectedBoard's data.
	 */
	public BoardDTO getSelectedBoard() {
		return BoardDTO.convert(selectedBoard);
	}

	/**
	 * Selects the board.
	 * 
	 * @param id of the Board to select.
	 */
	public void selectBoard(String id) {
		selectedBoard = boardRepo.getById(id);
	}

	/**
	 * Updates a Board with new data.
	 * 
	 * @param id          - Id of the Board to be updated.
	 * @param title       - The new title for the Board.
	 * @param description - The new description for the Board.
	 */
	public void updateBoard(String id, String title, String description) {
		Board board = boardRepo.getById(id);
		board.setTitle(title);
		board.setDescription(description);
		boardRepo.update(board);
	};

}
