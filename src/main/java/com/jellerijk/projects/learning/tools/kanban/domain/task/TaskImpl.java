package com.jellerijk.projects.learning.tools.kanban.domain.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.jellerijk.projects.learning.tools.kanban.utils.PublishedMessageType;
import com.jellerijk.projects.learning.tools.kanban.utils.Subscriber;

public class TaskImpl implements Task {
	private List<Subscriber> subscribers;

	private int id; // 0 = id not set yet
	private String description;
	private int boardId;
	private int stageNumber;
	private boolean completed;

	public TaskImpl(int id, String description, int boardId, int stageNumber, boolean completed) {
		this(description, boardId, stageNumber, completed);
		setId(id);
	}

	public TaskImpl(String description, int boardId, int stageNumber, boolean completed) {
		this.subscribers = new ArrayList<Subscriber>();
		setDescription(description);
		setBoardId(boardId);
		setStageNumber(stageNumber);
		setCompleted(completed);
	};

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

	/* SETTERS */
	private void setDescription(String description) {
		if (description == null || description.isBlank())
			throw new IllegalArgumentException("Task description is required.");
		this.description = description;
	}

	private void setBoardId(int boardId) {
		if (boardId < 0)
			throw new IllegalArgumentException("BoardId cannot be a negative number.");
		this.boardId = boardId;
	}

	private void setStageNumber(int stageNumber) {
		if (stageNumber < 0)
			throw new IllegalArgumentException("StageNumber cannot be a negative number.");
		this.stageNumber = stageNumber;
	}

	private void setCompleted(boolean completed) {
		this.completed = completed;
	}

	@Override
	public final void setId(int id) {
		if (this.id != 0)
			throw new IllegalArgumentException("Id was already set.");
		if (id < 0)
			throw new IllegalArgumentException("Task ID cannot be negative.");
		this.id = id;
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
