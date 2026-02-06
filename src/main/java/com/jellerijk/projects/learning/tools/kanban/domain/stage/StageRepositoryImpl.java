package com.jellerijk.projects.learning.tools.kanban.domain.stage;

import java.util.ArrayList;
import java.util.List;

public class StageRepositoryImpl implements StageRepository {
	private List<Stage> stages;

	public StageRepositoryImpl(List<Stage> stages) {
		setStages(stages);
	};

	public StageRepositoryImpl() {
		this(new ArrayList<Stage>());
	}

	@Override
	public void setStages(List<Stage> stages) {
		if (stages == null)
			throw new IllegalArgumentException("Supplied Stages repository was null.");
		this.stages = stages;
	}

	@Override
	public void add(Stage stage) {
		stages.add(stage);
	}

	@Override
	public void remove(int boardId, int stageNumber) {
		Stage stage = getStage(boardId, stageNumber);
		stages.remove(stage);
	}

	@Override
	public Stage getStage(int boardId, int stageNumber) {
		return stages.stream().filter(stage -> stage.getBoardId() == boardId && stage.getNumber() == stageNumber)
				.findFirst().orElseThrow();
	}

}
