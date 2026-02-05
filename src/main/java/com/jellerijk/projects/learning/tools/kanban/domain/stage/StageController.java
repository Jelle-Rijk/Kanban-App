package com.jellerijk.projects.learning.tools.kanban.domain.stage;

import com.jellerijk.projects.learning.tools.kanban.persistence.dto.StageDTO;

public interface StageController {
	/**
	 * Creates a new stage.
	 * 
	 * @param Stage data
	 */
	public void createStage(StageDTO data);

}
