package com.jellerijk.projects.learning.tools.kanban.domain;

import java.util.List;
import java.util.NoSuchElementException;

import com.jellerijk.projects.learning.tools.kanban.exceptions.DatabaseInsertException;
import com.jellerijk.projects.learning.tools.kanban.exceptions.DatabaseReadException;
import com.jellerijk.projects.learning.tools.kanban.exceptions.DatabaseUpdateException;

public interface Repository<T extends Identifiable> {
	public void add(T object) throws DatabaseInsertException;

	/**
	 * Returns a List<T> containing all the elements stored in the repository.
	 * 
	 * @return
	 * @throws DatabaseReadException
	 */
	public List<T> getAsList() throws DatabaseReadException;

	public T getById(String id) throws NoSuchElementException;

	public void update(T object) throws DatabaseUpdateException;

	public void delete(String id) throws DatabaseUpdateException;

}
