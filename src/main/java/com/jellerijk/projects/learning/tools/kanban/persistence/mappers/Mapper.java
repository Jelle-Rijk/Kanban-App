package com.jellerijk.projects.learning.tools.kanban.persistence.mappers;

import java.util.Collection;

public interface Mapper<T> {
	/**
	 * Returns a Collection of all entries associated with this datatype in the
	 * database.
	 * 
	 * @return Collection of all entries in the database associated with this
	 *         datatype.
	 */
	public Collection<T> getAll();
	
	/**
	 * Maps object to a corresponding Database entry.
	 * @param object
	 */
	public void insert(T object);

}
