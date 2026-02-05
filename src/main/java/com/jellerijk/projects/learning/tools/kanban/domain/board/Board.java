package com.jellerijk.projects.learning.tools.kanban.domain.board;

import com.jellerijk.projects.learning.tools.kanban.persistence.dto.BoardDTO;
import com.jellerijk.projects.learning.tools.kanban.utils.Publisher;

public interface Board extends Publisher {

	/**
	 * Returns the Board's id.
	 * 
	 * @return
	 */
	public int getId();

	/**
	 * Returns the Board's name.
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * Sets the Board's name.
	 * 
	 * @param name
	 */
	public void setName(String name);

	/**
	 * Returns the Board's description.
	 * 
	 * @return
	 */
	public String getDescription();

	/**
	 * Sets the Board's description.
	 * 
	 * @param description
	 */
	public void setDescription(String description);
	
	/**
	 * Takes in a dto and sets the data of this Board to the dto's data.
	 * @param dto
	 */
	public void updateData(BoardDTO dto);
	
}
