package com.jellerijk.projects.learning.tools.kanban.domain.stage;

import java.sql.SQLException;
import java.util.List;

import com.jellerijk.projects.learning.tools.kanban.persistence.dto.StageDTO;
import com.jellerijk.projects.learning.tools.kanban.utils.Publisher;
import com.jellerijk.projects.learning.tools.kanban.utils.Subscriber;

public interface StageController extends Publisher {
	/**
	 * Returns the id of the Board this StageController belongs to.
	 * 
	 * @return
	 */
	public int getBoardId();

	/**
	 * Creates a new stage.
	 * 
	 * @param Stage data
	 */
	public void createStage(StageDTO data) throws SQLException;

	/**
	 * Deletes the stage from the Board.
	 * 
	 * @param stageNumber
	 */
	public void deleteStage(int stageNumber);

	/**
	 * Returns all stages managed by this StageController.
	 * 
	 * @return
	 */
	public List<StageDTO> getStages();

	/**
	 * Returns the Stage's data.
	 * 
	 * @param stageNumber
	 * @return
	 */
	public StageDTO getStage(int stageNumber);

	/**
	 * Renames the chosen stage.
	 * 
	 * @param stageNumber - The stage to rename.
	 * @param title       - The new stage title.
	 */
	
	/**
	 * Returns the number of stages in this StageController's repository.
	 * @return
	 */
	public int countStages();
	
	public void renameStage(int stageNumber, String title);

	/**
	 * Subscribes to the specified Stage.
	 * @param sub
	 * @param stageNumber
	 */
	public void subscribeToStage(Subscriber sub, int stageNumber);

}
