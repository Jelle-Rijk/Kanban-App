package com.jellerijk.projects.learning.tools.kanban.persistence.inserters;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

import com.jellerijk.projects.learning.tools.kanban.persistence.database.DBController;
import com.jellerijk.projects.learning.tools.kanban.persistence.dto.TaskDTO;

public abstract class TaskInserter {
	private final static String SQL = "INSERT INTO Task(Description, Stage, BoardId, Completed) VALUES (?,?,?,?)";

	public static void insert(TaskDTO task) throws SQLException {
		PreparedStatement pstmt = DBController.getInstance().prepareStatement(SQL);
		executePreparedStatement(pstmt, task);
		pstmt.close();
	}

	public static void insert(Collection<TaskDTO> tasks) throws SQLException {
		PreparedStatement pstmt = DBController.getInstance().prepareStatement(SQL);
		for (TaskDTO task : tasks)
			executePreparedStatement(pstmt, task);
		pstmt.close();
	}

	private static void executePreparedStatement(PreparedStatement pstmt, TaskDTO task) throws SQLException {
		pstmt.setString(1, task.description());
		pstmt.setInt(2, task.stageNumber());
		pstmt.setInt(3, task.boardId());
		pstmt.setInt(4, task.completed() ? 1 : 0);
		pstmt.execute();
	}
}
