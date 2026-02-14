package com.jellerijk.projects.learning.tools.kanban.domain.board;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.jellerijk.projects.learning.tools.kanban.persistence.dto.BoardDTO;
import com.jellerijk.projects.learning.tools.kanban.utils.Subscriber;

public class BoardImpl implements Board {
	private final List<Subscriber> subscribers;

	private int id; // 0 = id not set
	private String name;
	private String description;

	public BoardImpl(String name, String description) {
		this.subscribers = new ArrayList<Subscriber>();
		setName(name);
		setDescription(description);
	}

	public BoardImpl(int id, String name, String description) {
		this(name, description);
		setId(id);
	};

	@Override
	public Collection<Subscriber> getSubscribers() {
		return subscribers;
	}

	public void setId(int id) {
		if (this.id != 0)
			throw new IllegalArgumentException("BoardId can only be set once.");
		if (id < 1)
			throw new IllegalArgumentException("The BoardId was smaller than 1.");
		this.id = id;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		if (name == null || name.isBlank())
			throw new IllegalArgumentException("Board's name was null or blank.");
		this.name = name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public void updateData(BoardDTO dto) {
		setName(dto.name());
		setDescription(dto.description());
		notifySubs();
	}

}
