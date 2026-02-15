package com.jellerijk.projects.learning.tools.kanban.exceptions;

public class DatabaseUpdateException extends RuntimeException {

	private static final long serialVersionUID = 9061978207591044389L;

	public DatabaseUpdateException() {
		super("An exception was thrown while updating the database.");
	}

	public DatabaseUpdateException(String message) {
		super(message);
	}

	public DatabaseUpdateException(String message, Throwable cause) {
		super(message, cause);
	}

	public DatabaseUpdateException(Throwable cause) {
		super(cause);
	}
}
