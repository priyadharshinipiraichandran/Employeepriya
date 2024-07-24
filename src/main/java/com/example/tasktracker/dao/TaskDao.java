package com.example.tasktracker.dao;

import com.example.tasktracker.model.Task;
import com.example.tasktracker.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class TaskDao {
    private static final String INSERT_TASK_SQL = "INSERT INTO task (employee_id, project, date, start_time, end_time, category, description) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String TOTAL_HOURS_SQL = "SELECT SUM(TIMESTAMPDIFF(SECOND, start_time, end_time)) as total_seconds FROM task WHERE employee_id = ? AND date = ?";
    private static final String LAST_END_TIME_SQL = "SELECT MAX(end_time) as last_end_time FROM task WHERE employee_id = ? AND date = ?";
    private static final String GET_EMPLOYEE_ID_SQL = "SELECT id FROM employee WHERE phno = ?";
    private static final String TASK_DURATIONS_BY_PHONENUMBER_AND_DATE = "SELECT category, SUM(TIMESTAMPDIFF(SECOND, start_time, end_time)) as total_seconds FROM task WHERE employee_id = (SELECT id FROM employee WHERE phno = ?) AND date = ? GROUP BY category";
    private static final String GET_ONGOING_TASKS_SQL = "SELECT * FROM task WHERE employee_id = ? AND end_time IS NULL";

    public void saveTask(Task task) throws SQLException {
        long totalSeconds = getTotalHours(task.getEmployeeId(), task.getDate());
        long taskDurationSeconds = task.getDurationInSeconds();

        long totalHoursWithNewTask = totalSeconds + taskDurationSeconds;

        if (totalHoursWithNewTask > 8 * 3600) {
            throw new SQLException("Total working hours exceed 8 hours");
        }

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_TASK_SQL)) {
            stmt.setInt(1, task.getEmployeeId());
            stmt.setString(2, task.getProject());
            stmt.setDate(3, java.sql.Date.valueOf(task.getDate()));
            stmt.setTime(4, java.sql.Time.valueOf(task.getStartTime()));
            stmt.setTime(5, task.getEndTime() != null ? java.sql.Time.valueOf(task.getEndTime()) : null);
            stmt.setString(6, task.getCategory());
            stmt.setString(7, task.getDescription());
            stmt.executeUpdate();
        }
    }

    public long getTotalHours(int employeeId, LocalDate date) throws SQLException {
        long totalSeconds = 0;
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(TOTAL_HOURS_SQL)) {
            stmt.setInt(1, employeeId);
            stmt.setDate(2, java.sql.Date.valueOf(date));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                totalSeconds = rs.getLong("total_seconds");
            }
        }
        return totalSeconds;
    }

    public LocalTime getLastEndTime(int employeeId, LocalDate date) throws SQLException {
        LocalTime lastEndTime = null;
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(LAST_END_TIME_SQL)) {
            stmt.setInt(1, employeeId);
            stmt.setDate(2, java.sql.Date.valueOf(date));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                java.sql.Time endTime = rs.getTime("last_end_time");
                if (endTime != null) {
                    lastEndTime = endTime.toLocalTime();
                }
            }
        }
        return lastEndTime;
    }

    public int getEmployeeIdByPhonenumber(String phonenumber) throws SQLException {
        int employeeId = 0;
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_EMPLOYEE_ID_SQL)) {
            stmt.setString(1, phonenumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                employeeId = rs.getInt("id");
            }
        }
        return employeeId;
    }

    public Map<String, Long> getTaskDurationsByPhonenumberAndDate(String phonenumber, LocalDate date) throws SQLException {
        Map<String, Long> taskDurations = new HashMap<>();
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(TASK_DURATIONS_BY_PHONENUMBER_AND_DATE)) {
            stmt.setString(1, phonenumber);
            stmt.setDate(2, java.sql.Date.valueOf(date));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String category = rs.getString("category");
                long totalSeconds = rs.getLong("total_seconds");
                taskDurations.put(category, totalSeconds);
            }
        }
        return taskDurations;
    }

    public List<Task> getOngoingTasks(int employeeId) throws SQLException {
        List<Task> tasks = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_ONGOING_TASKS_SQL)) {
            stmt.setInt(1, employeeId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Task task = new Task();
                task.setId(rs.getInt("id"));
                task.setEmployeeId(rs.getInt("employee_id"));
                task.setProject(rs.getString("project"));
                task.setDate(rs.getDate("date").toLocalDate());
                task.setStartTime(rs.getTime("start_time").toLocalTime());
                task.setEndTime(rs.getTime("end_time") != null ? rs.getTime("end_time").toLocalTime() : null);
                task.setCategory(rs.getString("category"));
                task.setDescription(rs.getString("description"));
                task.setDurationInSeconds(task.calculateDurationInSeconds());
                tasks.add(task);
            }
        }
        return tasks;
    }

    public void endCurrentTask(int employeeId, LocalTime endTime) throws SQLException {
        String query = "UPDATE task SET end_time = ? WHERE employee_id = ? AND end_time IS NULL ORDER BY start_time DESC LIMIT 1";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setTime(1, java.sql.Time.valueOf(endTime));
            stmt.setInt(2, employeeId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                System.out.println("No ongoing task found for employee ID: " + employeeId);
            } else {
                System.out.println("Task ended successfully for employee ID: " + employeeId);
            }
        }
    }


    public List<Task> getTasksByPhonenumberAndDateRange(String phonenumber, LocalDate startDate, LocalDate endDate) throws SQLException {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT * FROM task WHERE employee_id = (SELECT id FROM employee WHERE phno = ?) AND date BETWEEN ? AND ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, phonenumber);
            stmt.setDate(2, java.sql.Date.valueOf(startDate));
            stmt.setDate(3, java.sql.Date.valueOf(endDate));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Task task = new Task();
                task.setId(rs.getInt("id"));
                task.setEmployeeId(rs.getInt("employee_id"));
                task.setProject(rs.getString("project"));
                task.setDate(rs.getDate("date").toLocalDate());
                task.setStartTime(rs.getTime("start_time").toLocalTime());
                task.setEndTime(rs.getTime("end_time") != null ? rs.getTime("end_time").toLocalTime() : null);
                task.setCategory(rs.getString("category"));
                task.setDescription(rs.getString("description"));
                task.setDurationInSeconds(task.calculateDurationInSeconds());
                tasks.add(task);
            }
        }
        return tasks;
    }
    public Map<LocalDate, Long> getDurationsByDate(String phonenumber, LocalDate startDate, LocalDate endDate) throws SQLException {
        Map<LocalDate, Long> durationsByDate = new HashMap<>();

        String sql = "SELECT date, SUM(TIMESTAMPDIFF(SECOND, start_time, end_time)) as total_seconds " +
                     "FROM task WHERE employee_id = (SELECT id FROM employee WHERE phno = ?) " +
                     "AND date BETWEEN ? AND ? GROUP BY date";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, phonenumber);
            preparedStatement.setDate(2, java.sql.Date.valueOf(startDate));
            preparedStatement.setDate(3, java.sql.Date.valueOf(endDate));

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                LocalDate date = resultSet.getDate("date").toLocalDate();
                long totalSeconds = resultSet.getLong("total_seconds");
                durationsByDate.put(date, totalSeconds);
            }
        }

        return durationsByDate;
    }
}
