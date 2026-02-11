package com.jellerijk.projects.learning.tools.kanban.domain.task;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import com.jellerijk.projects.learning.tools.kanban.logging.Logger;
import com.jellerijk.projects.learning.tools.kanban.persistence.database.DBController;
import com.jellerijk.projects.learning.tools.kanban.persistence.dto.TaskDTO;
import com.jellerijk.projects.learning.tools.kanban.utils.PublishedMessageType;
import com.jellerijk.projects.learning.tools.kanban.utils.Subscriber;

public class TaskImpl implements Task {
	private List<Subscriber> subscribers;

	private final int id;
	private String description;
	private int boardId;
	private int stageNumber;
	private boolean completed;

	public TaskImpl(TaskDTO data) {
		this(data.id(), data.description(), data.boardId(), data.stageNumber(), data.completed());
	}

	public TaskImpl(int id, String description, int boardId, int stageNumber, boolean completed) {
		if (id < 0)
			throw new IllegalArgumentException("Task ID cannot be negative.");
		this.id = id;
		setDescription(description);
		setBoardId(boardId);
		setStageNumber(stageNumber);
		setCompleted(completed);
	}

	@Override
	public void move(int stageNumber) {
		setStageNumber(stageNumber);
		notifySubs(PublishedMessageType.OBJECT_UPDATE);
	}

	@Override
	public void complete() {
		setCompleted(true);
		notifySubs(PublishedMessageType.OBJECT_UPDATE);
	}

	/* DATABASE */
	private void updateDatabase(String column, String value) throws SQLException {
		PreparedStatement stmt = prepareStatement(column);
		stmt.setString(1, value);
		updateDatabase(stmt);
	}

	private void updateDatabase(String column, int value) throws SQLException {
		PreparedStatement stmt = prepareStatement(column);
		stmt.setInt(1, value);
		updateDatabase(stmt);
	}

	/**
	 * Only supposed to be used by the other updateDatabase() methods.
	 * 
	 * @param column
	 * @throws SQLException
	 */
	private void updateDatabase(PreparedStatement stmt) throws SQLException {
		stmt.setInt(2, id);
		stmt.execute();
		stmt.close();
	};

	private PreparedStatement prepareStatement(String column) throws SQLException {
		String sql = String.format("UPDATE Task SET %s = ? WHERE TaskId = ?", column);
		return DBController.getInstance().prepareStatement(sql);
	}

	/* SETTERS */
	private void setDescription(String description) {
		if (description == null || description.isBlank())
			throw new IllegalArgumentException("Task description is required.");
		try {
			updateDatabase("Description", description);
			this.description = description;
		} catch (SQLException ex) {
			Logger.logError("Something went wrong while setting task description.");
			Logger.logError(ex);
		}

	}

	private void setBoardId(int boardId) {
		if (boardId < 0)
			throw new IllegalArgumentException("BoardId cannot be a negative number.");
		try {
			updateDatabase("BoardId", boardId);
			this.boardId = boardId;
		} catch (SQLException ex) {
			Logger.logError("Something went wrong while setting task board.");
			Logger.logError(ex);
		}
	}

	private void setStageNumber(int stageNumber) {
		if (stageNumber < 0)
			throw new IllegalArgumentException("StageNumber cannot be a negative number.");
		try {
			updateDatabase("Stage", stageNumber);
			this.stageNumber = stageNumber;
		} catch (SQLException ex) {
			Logger.logError("Something went wrong while setting task stage.");
			Logger.logError(ex);
		}
	}

	private void setCompleted(boolean completed) {
		try {
			updateDatabase("Completed", completed ? 1 : 0);
			this.completed = completed;
		} catch (SQLException ex) {
			Logger.logError("Something went wrong while setting task completion.");
			Logger.logError(ex);
		}
	}

	/* GETTERS */
	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public int getStageNumber() {
		return stageNumber;
	}

	@Override
	public int getBoardId() {
		return boardId;
	}

	@Override
	public boolean isCompleted() {
		return completed;
	}

	@Override
	public Collection<Subscriber> getSubscribers() {
		return subscribers;
	}

}
