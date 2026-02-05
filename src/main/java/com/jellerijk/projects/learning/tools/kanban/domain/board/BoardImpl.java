package com.jellerijk.projects.learning.tools.kanban.domain.board;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.jellerijk.projects.learning.tools.kanban.persistence.dto.BoardDTO;
import com.jellerijk.projects.learning.tools.kanban.utils.Subscriber;

public class BoardImpl implements Board {
	private final int id;
	private final List<Subscriber> subscribers;
	private String name;
	private String description;

	public BoardImpl(int id, String name, String description) {
		this(id);
		setName(name);
		setDescription(description);
	};

	public BoardImpl(int id) {
		if (id < 0)
			throw new IllegalArgumentException("A board's ID needs to be a positive integer.");
		this.id = id;
		this.subscribers = new ArrayList<>();
	}

	@Override
	public Collection<Subscriber> getSubscribers() {
		return subscribers;
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
