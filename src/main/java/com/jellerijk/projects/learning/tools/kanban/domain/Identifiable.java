package com.jellerijk.projects.learning.tools.kanban.domain;

public interface Identifiable {
	/**
	 * Returns this Identifiable's identifier.
	 * 
	 * @return UUID for this Identifiable.
	 */
	public String getId();
}
