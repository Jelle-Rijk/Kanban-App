package com.jellerijk.projects.learning.tools.kanban.domain.board;

import java.util.List;

import com.jellerijk.projects.learning.tools.kanban.persistence.dto.BoardDTO;
import com.jellerijk.projects.learning.tools.kanban.utils.Publisher;
import com.jellerijk.projects.learning.tools.kanban.utils.Subscriber;

public interface BoardController extends Publisher {
	public static BoardController getInstance() {
		return BoardControllerImpl.getInstance();
	};

	/**
	 * Creates a new Board with the supplied name and description.
	 * 
	 * @param name
	 * @param description
	 */
	public void createBoard(String name, String description);

	/**
	 * Gets a Board by its id.
	 * 
	 * @param id
	 * @return BoardDTO the board's data
	 */
	public BoardDTO getBoard(int id);

	/**
	 * Updates the board with the corresponding id.
	 * 
	 * @param updatedBoard
	 */
	public void updateBoard(int id, BoardDTO updatedBoard);

	/**
	 * Deletes the Board with the corresponding Id.
	 * 
	 * @param id
	 */
	public void deleteBoard(int id);

	public List<BoardDTO> getBoards();

	/**
	 * Subscribers sub to the Board of the given Id.
	 * 
	 * @param sub
	 * @param boardId - The Board to subscribe to
	 */
	public void subscribe(Subscriber sub, int boardId);

}
