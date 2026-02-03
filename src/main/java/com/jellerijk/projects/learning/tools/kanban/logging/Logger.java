package com.jellerijk.projects.learning.tools.kanban.logging;

import java.time.LocalDate;
import java.time.LocalTime;

public abstract class Logger {
	public static void log(String message) {
		writeLog("info", message);
	}

	public static void logError(String message) {
		writeLog("error", message);
	}

	private static void writeLog(String type, String message) {
		if (type.equals("error")) {
			System.err.printf("%s %s - %s - %s%n", LocalDate.now(), LocalTime.now(), type.toUpperCase(), message);
			return;
		}
		System.out.printf("%s %s - %s - %s%n", LocalDate.now(), LocalTime.now(), type.toUpperCase(), message);
	}
}
