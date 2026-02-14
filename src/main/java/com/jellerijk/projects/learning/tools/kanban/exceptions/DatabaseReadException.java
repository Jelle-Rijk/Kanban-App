package com.jellerijk.projects.learning.tools.kanban.exceptions;

public class DatabaseReadException extends RuntimeException {

	private static final long serialVersionUID = -7073436507770287877L;

	public DatabaseReadException() {
		super("An exception was thrown while reading from the database.");
	}

	public DatabaseReadException(String message) {
		super(message);
	}

	public DatabaseReadException(String message, Throwable cause) {
		super(message, cause);
	}

	public DatabaseReadException(Throwable cause) {
		super(cause);
	}
}
