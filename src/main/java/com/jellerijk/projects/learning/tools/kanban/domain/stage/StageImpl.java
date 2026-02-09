package com.jellerijk.projects.learning.tools.kanban.domain.stage;

import java.sql.PreparedStatement;

import com.jellerijk.projects.learning.tools.kanban.logging.Logger;
import com.jellerijk.projects.learning.tools.kanban.persistence.database.DBController;

public class StageImpl implements Stage {
	private int number;
	private int boardId;
	private String title;
	private String description;
	private int limit;

	public StageImpl(int number, int boardId, String title, String description, int limit) {
		setNumber(number);
		setBoardId(boardId);
		setTitle(title);
		setDescription(description);
		setLimit(limit);
	}

	@Override
	public int getNumber() {
		return number;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public int getBoardId() {
		return boardId;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public int getLimit() {
		return limit;
	}

	private void setBoardId(int boardId) {
		if (number < 0)
			throw new IllegalArgumentException("Stage's BoardId must not be negative.");
		this.boardId = boardId;
	}

	private void setNumber(int number) {
		if (number < 0)
			throw new IllegalArgumentException("Stage number must not be negative.");
		this.number = number;
	}

	private void setTitle(String title) {
		if (title == null || title.isBlank())
			throw new IllegalArgumentException("Stage title cannot be empty.");
		this.title = title;
	}

	private void setDescription(String description) {
		this.description = description;
	}

	private void setLimit(int limit) {
		if (number < 0)
			throw new IllegalArgumentException("Stage task limit must not be negative.");
		this.limit = limit;
	}

	@Override
	public void rename(String title) {
		if (this.title.equals(title))
			return;
		
		try {
			setTitle(title);
			String sql = "UPDATE Stage SET title = ? WHERE Number = ? AND BoardId = ?";
			PreparedStatement stmt = DBController.getInstance().prepareStatement(sql);
			stmt.setString(1, title);
			stmt.setInt(2, number);
			stmt.setInt(3, boardId);
		} catch (Exception e) {
			Logger.logError("Encountered an error while trying to rename the stage.");
			e.printStackTrace();
		}

	}

}
