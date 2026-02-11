package com.jellerijk.projects.learning.tools.kanban.persistence.loaders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.jellerijk.projects.learning.tools.kanban.logging.Logger;
import com.jellerijk.projects.learning.tools.kanban.persistence.database.DBController;
import com.jellerijk.projects.learning.tools.kanban.persistence.dto.TaskDTO;

public abstract class TaskLoader {

	public static List<TaskDTO> loadTasksByStage(int boardId, int stageNumber) {
		try {
			PreparedStatement stmt = DBController.getInstance()
					.prepareStatement("SELECT * FROM Task WHERE boardId = ? AND stageNumber = ?");
			stmt.setInt(1, boardId);
			stmt.setInt(2, stageNumber);
			ResultSet result = stmt.executeQuery();
			return convertResultSet(result);
		} catch (SQLException sqlEx) {
			Logger.logError("Encountered an error while loading tasks by stage from the database.");
			Logger.logError(sqlEx.getMessage());
		}
		return new ArrayList<TaskDTO>();
	}

	public static List<TaskDTO> loadTasksForBoard(int boardId) {
		try {
			PreparedStatement stmt = DBController.getInstance()
					.prepareStatement("SELECT * FROM Task WHERE BoardId = ?");
			stmt.setInt(1, boardId);
			ResultSet result = stmt.executeQuery();
			return convertResultSet(result);
		} catch (SQLException sqlEx) {
			Logger.logError("Encountered an error while loading tasks for board from the database.");
			Logger.logError(sqlEx.getMessage());
		}
		return new ArrayList<TaskDTO>();
	}

	public static TaskDTO get(int id) throws SQLException {
		PreparedStatement stmt = DBController.getInstance().prepareStatement("SELECT * FROM Task WHERE TaskId = ?");
		stmt.setInt(1, id);
		ResultSet result = stmt.executeQuery();
		return convertResultSet(result).getFirst();
	}

	public static Collection<TaskDTO> loadAll() {
		ResultSet results = DBController.getInstance().query("SELECT * FROM Task");
		return convertResultSet(results);
	};

	private static List<TaskDTO> convertResultSet(ResultSet results) {
		List<TaskDTO> tasks = new ArrayList<TaskDTO>();
		try {
			while (results.next()) {
				int taskId = results.getInt("TaskId");
				String description = results.getString("Description");
				int boardId = results.getInt("BoardId");
				int stageNumber = results.getInt("Stage");

				TaskDTO task = TaskDTO.create(taskId, description, boardId, stageNumber, false);
				tasks.add(task);
			}
			results.close();
		} catch (SQLException e) {
			Logger.logError("Something went wrong while trying to convert ResultSet to Tasks.");
			e.printStackTrace();
		}
		return tasks;
	}
}
