package com.jellerijk.projects.learning.tools.kanban.config;

import java.nio.file.Path;

public abstract class GlobalVars {
	public static final String DB_PATH = Path
			.of(System.getProperty("user.home"), "JR_Custom_Tools", "Kanban", "database.sqlite").toString();
}
