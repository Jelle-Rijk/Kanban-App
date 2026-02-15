package com.jellerijk.projects.learning.tools.kanban.domain.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.jellerijk.projects.learning.tools.kanban.persistence.dto.TaskDTO;
import com.jellerijk.projects.learning.tools.kanban.utils.PublishedMessageType;
import com.jellerijk.projects.learning.tools.kanban.utils.Subscriber;

public class TaskControllerImpl implements TaskController {
	private static TaskController instance;

	private TaskRepository taskRepo;

	private final List<Subscriber> subs;

	private TaskControllerImpl() {
		this.subs = new ArrayList<Subscriber>();
		this.taskRepo = new TaskRepositoryImpl();
	}

	public static TaskController getInstance() {
		if (instance == null)
			instance = new TaskControllerImpl();
		return instance;
	}

	@Override
	public int createTask(String description, int board, int stageNumber) {
		try {
			Task task = new TaskImpl(description, board, stageNumber, false);
			int id = taskRepo.addTask(task);
			notifySubs(PublishedMessageType.REPO_UPDATE);
			return id;
		} catch (Exception ex) {
			throw new RuntimeException("Something went wrong while creating a task.", ex);
		}
	}

	@Override
	public void moveTask(int id, int stageNumber) {
		Task task = taskRepo.getTask(id);
		task.move(stageNumber);
		notifySubs(PublishedMessageType.REPO_UPDATE);
	}

	@Override
	public void deleteTask(int id) {
		taskRepo.remove(id);
		notifySubs(PublishedMessageType.REPO_UPDATE);
	}

	@Override
	public void completeTask(int id) {
		Task task = taskRepo.getTask(id);
		task.complete();
	}

	@Override
	public TaskDTO getTask(int id) {
		Task task = taskRepo.getTask(id);
		return TaskDTO.convert(task);
	}

	@Override
	public List<TaskDTO> getTasksForStage(int stageNumber, int boardId) {
		return taskRepo.getTasksByStage(boardId, stageNumber).stream().map(task -> TaskDTO.convert(task))
				.collect(Collectors.toCollection(ArrayList::new));
	}

//	HELPER METHODS

	@Override
	public Collection<Subscriber> getSubscribers() {
		return subs;
	}

	@Override
	public void subscribeToTask(Subscriber sub, int id) {
		Task task = taskRepo.getTask(id);
		task.subscribe(sub);
	}

}
