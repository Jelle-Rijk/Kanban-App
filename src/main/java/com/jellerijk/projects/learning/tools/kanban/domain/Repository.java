package com.jellerijk.projects.learning.tools.kanban.domain;

public interface Repository<T> {
	public String add(T object);

	public T getById(String id) throws IllegalArgumentException;

	public void update(T object);

	public void delete(String id) throws IllegalArgumentException;

}
