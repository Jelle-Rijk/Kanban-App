package com.jellerijk.projects.learning.tools.kanban.domain.stage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.jellerijk.projects.learning.tools.kanban.utils.Subscriber;

// TODO: Change rename, delete and changeNumber methods
public class StageImpl implements Stage {
	private final List<Subscriber> subs;

	private int id; // 0 = not set
	private int number;
	private int boardId;
	private String title;
	private String description;
	private int limit;

	public StageImpl(int number, int boardId, String title) {
		setNumber(number);
		setBoardId(boardId);
		setTitle(title);
		this.subs = new ArrayList<Subscriber>();
	}

	public StageImpl(int id, int number, int boardId, String title) {
		this(number, boardId, title);
		setId(id);

	}

	public StageImpl(int number, int boardId, String title, String description, int limit) {

		setDescription(description);
		setLimit(limit);
		this.subs = new ArrayList<Subscriber>();
	}

	@Override
	public int getId() {
		return id;
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

	@Override
	public final void setNumber(int number) {
		if (number < 0)
			throw new IllegalArgumentException("Stage number must not be negative.");
		this.number = number;
	}

	@Override
	public final void setTitle(String title) {
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
	public final void setId(int id) {
		if (this.id != 0)
			throw new IllegalArgumentException(String.format("Id was already set [this.id=%d]", this.id));
		if (id < 1)
			throw new IllegalArgumentException("Invalid Id, must be at least 1.");
		this.id = id;
	};

	@Override
	public Collection<Subscriber> getSubscribers() {
		return subs;
	}
}
