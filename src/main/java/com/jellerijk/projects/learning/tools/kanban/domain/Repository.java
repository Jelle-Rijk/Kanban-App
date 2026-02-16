package com.jellerijk.projects.learning.tools.kanban.domain;

public interface Repository<T> {
	public int add(T object);

	public T getById(String id) throws IllegalArgumentException;

	public int update(T object);

	public void delete(String id) throws IllegalArgumentException;

}
