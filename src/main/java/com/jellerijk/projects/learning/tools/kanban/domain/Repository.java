package com.jellerijk.projects.learning.tools.kanban.domain;

import java.util.List;
import java.util.NoSuchElementException;

public interface Repository<T extends Identifiable> {
	public String add(T object);

	public List<T> getAll();

	public default T getById(String id) throws NoSuchElementException {
		return getAll().stream().filter(element -> element.getId().equals(id)).findFirst().orElseThrow();
	}

	public void update(T object);

	public void delete(String id) throws IllegalArgumentException;

}
