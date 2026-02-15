package com.jellerijk.projects.learning.tools.kanban.domain.stage;

import java.util.List;

public interface StageRepository {
	/**
	 * Sets the StageRepository to the supplied list.
	 * 
	 * @param stages
	 */
	public void setStages(List<Stage> stages);

	/**
	 * Returns the Repository's list.
	 * 
	 * @return
	 */
	public List<Stage> getStages();

	/**
	 * Adds a Stage to the repository.
	 * 
	 * @param stage
	 */
	public void add(Stage stage);

	/**
	 * Removes a Stage from the repository.
	 * 
	 * @param stageNumber
	 */
	public void remove(Stage stage);

	/**
	 * Gets the Stage from the repository.
	 * 
	 * @param boardId
	 * @param stageNumber
	 * @return
	 */
	public Stage getStage(int stageNumber, int boardId);

	/**
	 * Rename the chosen stage.
	 * 
	 * @param stageNumber
	 * @param boardId
	 * @param name
	 * @return
	 */
	public void rename(int stageNumber, int boardId, String name);

}
