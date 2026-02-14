package com.jellerijk.projects.learning.tools.kanban.logging;

import java.time.LocalDate;
import java.time.LocalTime;

public abstract class Logger {
	/**
	 * Logs the message as an info message.
	 * 
	 * @param message
	 */
	public static void log(String message) {
		writeLog("info", message);
	}

	public static void logDebug(Object source, String message) {
		logDebug(String.format("(%s) %s", source.getClass().getSimpleName(), message));
	}

	public static void logDebug(String message) {
		writeLog("debug", message);
	}

	/**
	 * Logs the supplied message as an error.
	 * 
	 * @param message
	 */
	public static void logError(String message) {
		writeLog("error", message);
	}

	/**
	 * Takes in an exception and logs its type and message as an error.
	 * 
	 * @param ex
	 */
	public static void logError(Exception ex) {
		logError(String.format("(%s) %s", ex.getClass().getSimpleName(), ex.getMessage()));
	}

	private static void writeLog(String type, String message) {
		if (type.equals("error")) {
			System.err.printf("%s %s - %s - %s%n", LocalDate.now(), LocalTime.now(), type.toUpperCase(), message);
			return;
		}
		System.out.printf("%s %s - %s - %s%n", LocalDate.now(), LocalTime.now(), type.toUpperCase(), message);
	}
}
