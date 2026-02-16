package com.jellerijk.projects.learning.tools.kanban.domain;

public class Task implements Identifiable {
	private final String id;
	private String description;
	private String details;

	public Task(String id, String description) {
		verifyId(id);
		this.id = id;
		setDescription(description);
	}

	public Task(String id, String description, String details) {
		this(id, description);
		setDetails(details);
	}

	/* VERIFICATION */
	private void verifyId(String id) {
		if (id == null || id.isBlank())
			throw new IllegalArgumentException("Invalid id for Task.");
	}

	/* GETTERS - SETTERS */
	@Override
	public String getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	private void setDescription(String description) {
		this.description = description;
	}

	public String getDetails() {
		return details;
	}

	private void setDetails(String details) {
		this.details = details;
	}

}
