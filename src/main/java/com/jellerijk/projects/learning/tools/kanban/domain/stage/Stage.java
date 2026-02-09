package com.jellerijk.projects.learning.tools.kanban.domain.stage;

import com.jellerijk.projects.learning.tools.kanban.utils.Publisher;

public interface Stage extends Publisher {
	public int getNumber();

	public String getTitle();

	public int getBoardId();

	public String getDescription();

	public int getLimit();

	/**
	 * Updates this Stage's name.
	 * @param title
	 */
	public void rename(String title);

	/**
	 * Deletes this Stage from the database.
	 */
	public void delete();
}
