package com.jellerijk.projects.learning.tools.kanban.domain.stage;

public interface Stage {
	public int getNumber();

	public String getTitle();

	public int getBoardId();

	public String getDescription();

	public int getLimit();

	public void rename(String title);
}
