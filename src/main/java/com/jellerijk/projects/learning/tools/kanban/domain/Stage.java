package com.jellerijk.projects.learning.tools.kanban.domain;

public class Stage implements Identifiable {

	private final String id;
	private String name;
	private int limit;

	public Stage(String id, String name) {
		verifyId(id);
		this.id = id;
		setName(name);
	}

	public Stage(String id, String name, int limit) {
		this(id, name);
		setLimit(limit);
	}

	/* VERIFICATION */
	private void verifyId(String id) {
		if (id == null || id.isBlank())
			throw new IllegalArgumentException("Invalid id for Stage");
	}

	/* GETTERS - SETTERS */
	@Override
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	private void setName(String name) {
		if (name == null || name.isBlank())
			throw new IllegalArgumentException("Every Stage needs a name.");
		this.name = name;
	}

	public int getLimit() {
		return limit;
	}

	private void setLimit(int limit) {
		this.limit = limit;
	}

}
