package com.jellerijk.projects.learning.tools.kanban.domain.stage;

import java.util.ArrayList;
import java.util.List;

import com.jellerijk.projects.learning.tools.kanban.logging.Logger;
import com.jellerijk.projects.learning.tools.kanban.persistence.mappers.StageMapper;

public class StageRepositoryImpl implements StageRepository {
	private StageMapper mapper;
	private List<Stage> stages;

	public StageRepositoryImpl() {
		mapper = new StageMapper();
		setStages((ArrayList<Stage>) mapper.getAll());
	};

	@Override
	public void setStages(List<Stage> stages) {
		if (stages == null)
			throw new IllegalArgumentException("Supplied Stages repository was null.");
		this.stages = stages;
	}

	@Override
	public void add(Stage stage) {
		try {
			mapper.insert(stage);
			stages.add(stage);
		} catch (Exception ex) {
			Logger.logError("An exception occured while adding a new Stage.");
			Logger.logError(ex);
		}
	}

	@Override
	public void remove(Stage stage) {
		try {
			mapper.delete(stage);
			stages.remove(stage);
		} catch (Exception ex) {
			Logger.logError("Error occured while removing stage.");
			Logger.logError(ex);
		}
	}

	@Override
	public Stage getStage(int boardId, int stageNumber) {
		return stages.stream().filter(stage -> stage.getBoardId() == boardId && stage.getNumber() == stageNumber)
				.findFirst().orElseThrow();
	}

	@Override
	public List<Stage> getStages() {
		return stages;
	}

}
