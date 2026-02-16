package com.jellerijk.projects.learning.tools.kanban.domain;

public class Board implements Identifiable {
	private final String id;
	private String title;
	private String description;

	// MIN ARGS
	public Board(String id, String title) {
		verifyId(id);
		this.id = id;
		setTitle(title);
	}

	public Board(String id, String title, String description) {
		this(id, title);
		setDescription(description);
	}

	/*
	 * VERIFICATION
	 */
	private void verifyId(String id) {
		if (id == null || id.isBlank())
			throw new IllegalArgumentException();
	}

	/*
	 * GETTERS - SETTERS
	 */
	@Override
	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	private void setTitle(String title) {
		if (title == null || title.isBlank())
			throw new IllegalArgumentException("Every Board needs a title.");
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	private void setDescription(String description) {
		if (title.isBlank())
			throw new IllegalArgumentException("Description cannot be an empty String.");
		this.description = description;
	}

}
