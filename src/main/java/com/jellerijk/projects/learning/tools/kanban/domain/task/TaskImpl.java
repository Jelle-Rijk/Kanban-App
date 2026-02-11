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

	private static PreparedStatement updateStatement;

	public TaskImpl(TaskDTO data) {
		if (data.id() < 0)
			throw new IllegalArgumentException("Task ID cannot be negative.");
		id = data.id();
		updateData(data);
	}

	@Override
	public void move(int stageNumber) {
		try {
			setStageNumber(stageNumber);
			notifySubs(PublishedMessageType.OBJECT_UPDATE);
		} catch (SQLException ex) {
			Logger.logError(ex);
		}
	}

	@Override
	public void updateData(TaskDTO data) {
		try {
			setDescription(data.description());
			setBoardId(data.boardId());
			setStageNumber(data.stageNumber());
			setCompleted(data.completed());
		} catch (SQLException ex) {
			Logger.logError(ex);
		}
		notifySubs(PublishedMessageType.OBJECT_UPDATE);
	}

	@Override
	public void complete() {
		try {
			setCompleted(true);			
			notifySubs(PublishedMessageType.OBJECT_UPDATE);
		} catch (SQLException ex) {
			Logger.logError(ex);
		}
	}

	/* DATABASE */
	private void updateDatabase(String column, String value) throws SQLException {
		PreparedStatement stmt = getUpdateStatement();
		stmt.setString(2, value);
		updateDatabase(stmt, column);
	}

	private void updateDatabase(String column, int value) throws SQLException {
		PreparedStatement stmt = getUpdateStatement();
		updateStatement.setInt(2, value);
		updateDatabase(stmt, column);
	}

	/**
	 * Only supposed to be used by the other updateDatabase() methods.
	 * 
	 * @param column
	 * @throws SQLException
	 */
	private void updateDatabase(PreparedStatement stmt, String column) throws SQLException {
		stmt.setString(1, column);
		stmt.setInt(3, id);
		stmt.execute();
	};

	private PreparedStatement getUpdateStatement() throws SQLException {
		if (updateStatement == null)
			updateStatement = DBController.getInstance().prepareStatement("UPDATE Task SET ? = ? WHERE TaskId = ?");
		return updateStatement;
	}

	/* SETTERS */
	private void setDescription(String description) throws SQLException {
		if (description == null || description.isBlank())
			throw new IllegalArgumentException("Task description is required.");
		updateDatabase("Description", description);
		this.description = description;

	}

	private void setBoardId(int boardId) throws SQLException {
		if (boardId < 0)
			throw new IllegalArgumentException("BoardId cannot be a negative number.");
		updateDatabase("BoardId", boardId);
		this.boardId = boardId;
	}

	private void setStageNumber(int stageNumber) throws SQLException {
		if (stageNumber < 0)
			throw new IllegalArgumentException("StageNumber cannot be a negative number.");
		updateDatabase("Stage", stageNumber);
		this.stageNumber = stageNumber;
	}

	private void setCompleted(boolean completed) throws SQLException {
		updateDatabase("Completed", completed ? 1 : 0);
		this.completed = completed;
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
