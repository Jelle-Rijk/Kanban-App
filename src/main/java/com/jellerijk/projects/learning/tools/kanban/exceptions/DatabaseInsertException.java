package com.jellerijk.projects.learning.tools.kanban.exceptions;

public class DatabaseInsertException extends RuntimeException {

	private static final long serialVersionUID = 2836189319739982469L;

	public DatabaseInsertException() {
		super("An exception was thrown while inserting into the database.");
	}

	public DatabaseInsertException(String message) {
		super(message);
	}

	public DatabaseInsertException(String message, Throwable cause) {
		super(message, cause);
	}

	public DatabaseInsertException(Throwable cause) {
		super(cause);
	}
}
