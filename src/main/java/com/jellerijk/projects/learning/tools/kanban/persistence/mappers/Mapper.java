package com.jellerijk.projects.learning.tools.kanban.persistence.mappers;

import java.util.Collection;

import com.jellerijk.projects.learning.tools.kanban.exceptions.DatabaseInsertException;
import com.jellerijk.projects.learning.tools.kanban.exceptions.DatabaseReadException;

public interface Mapper<T> {
	/**
	 * Maps object to a corresponding Database entry.
	 * 
	 * @param object
	 * @return Returns the object's autoincrement id or -1 if the object's table is
	 *         not auto-incremented.
	 */
	public int insert(T object) throws DatabaseInsertException;

	/**
	 * Returns a Collection of all entries associated with this datatype in the
	 * database.
	 * 
	 * @return Collection of all entries in the database associated with this
	 *         datatype.
	 */
	public Collection<T> getAll() throws DatabaseReadException;

	/**
	 * Removes the entry associated to this object from the database.
	 * 
	 * @param object
	 */
	public void delete(T object);

}
