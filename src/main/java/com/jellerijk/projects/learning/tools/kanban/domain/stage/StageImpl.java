package com.jellerijk.projects.learning.tools.kanban.domain.stage;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.jellerijk.projects.learning.tools.kanban.logging.Logger;
import com.jellerijk.projects.learning.tools.kanban.persistence.database.DBController;
import com.jellerijk.projects.learning.tools.kanban.utils.Subscriber;

public class StageImpl implements Stage {
	private final List<Subscriber> subs;

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
		this.subs = new ArrayList<Subscriber>();
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
		if (this.title != null && this.title.equals(title))
			throw new IllegalArgumentException("New title was the same as old title.");
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
		try {
			setTitle(title);
			String sql = "UPDATE Stage SET title = ? WHERE Number = ? AND BoardId = ?";
			PreparedStatement stmt = DBController.getInstance().prepareStatement(sql);
			stmt.setString(1, title);
			stmt.setInt(2, number);
			stmt.setInt(3, boardId);
			stmt.execute();
			Logger.log("Trying to rename Stage.");
		} catch (Exception e) {
			Logger.logError(String.format("Encountered an error while trying to rename the stage: %s", e.getMessage()));
		}
		notifySubs();

	}

	@Override
	public Collection<Subscriber> getSubscribers() {
		return subs;
	}

	@Override
	public void delete() {
		String sql = String.format("DELETE FROM Stage WHERE BoardId = %d AND Number = %d", boardId, number);
		DBController.getInstance().update(sql);
	}

	@Override
	public void changeNumber(int stageNumber) {
		String sql = "UPDATE Stage SET Number = ? WHERE BoardId = ? AND Number = ?";
		PreparedStatement stmt;
		try {
			stmt = DBController.getInstance().prepareStatement(sql);
			stmt.setInt(1, stageNumber);
			stmt.setInt(2, boardId);
			stmt.setInt(3, number);
			setNumber(stageNumber);
			stmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
