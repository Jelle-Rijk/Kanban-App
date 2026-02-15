package com.jellerijk.projects.learning.tools.kanban.domain.stage;

import java.sql.SQLException;
import java.util.List;

import com.jellerijk.projects.learning.tools.kanban.persistence.dto.StageDTO;
import com.jellerijk.projects.learning.tools.kanban.utils.Publisher;
import com.jellerijk.projects.learning.tools.kanban.utils.Subscriber;

public interface StageController extends Publisher {
	public static StageController getInstance() {
		return StageControllerImpl.getInstance();
	}

	/**
	 * Creates a new stage.
	 * 
	 * @param Stage data
	 */
	public void createStage(int number, int boardId, String title) throws SQLException;

	/**
	 * Deletes the stage from the Board.
	 * 
	 * @param stageNumber
	 * @param boardId
	 */
	public void deleteStage(int number, int boardId);

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
	public StageDTO getStage(int stageNumber, int boardId);

	/**
	 * Renames the chosen stage.
	 * 
	 * @param stageNumber - The stage to rename.
	 * @param title       - The new stage title.
	 */

	/**
	 * Returns the number of stages belonging to the supplied board.
	 * 
	 * @param boardId - the id of the board of which to count the stages.
	 * @return number of stages
	 */
	public int countStages(int boardId);

	/**
	 * Renames the supplied Stage.
	 * @param stageNumber
	 * @param boardId
	 * @param title
	 */
	public void renameStage(int stageNumber, int boardId, String title);

	/**
	 * Subscribes to the specified Stage.
	 * 
	 * @param sub
	 * @param stageNumber
	 */
	public void subscribeToStage(Subscriber sub, int stageNumber, int boardId);

}
