package com.jellerijk.projects.learning.tools.kanban.domain.task;

public interface Task {
	public int getId();

	public String getDescription();

	public int getStageNumber();

	public int getBoardId();
}
