package com.jellerijk.projects.learning.tools.kanban.domain.stage;

import java.sql.SQLException;
import java.util.List;

import com.jellerijk.projects.learning.tools.kanban.persistence.dto.StageDTO;

public interface StageController {
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
	 * @return
	 */
	public List<StageDTO> getStages();

}
