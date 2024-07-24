package com.example.tasktracker.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.tasktracker.util.DatabaseUtil;

public class UserDao {
    // Method to check if old password matches based on phone number
    public boolean checkPasswordByPhno(String phno, String password) throws SQLException {
        String sql = "SELECT * FROM employee WHERE phno = ? AND password = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, phno);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // true if match found, false otherwise
        }
    }

    // Method to update password based on phone number
    public void updatePasswordByPhno(String phno, String newPassword) throws SQLException {
        String sql = "UPDATE employee SET password = ? WHERE phno = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newPassword);
            stmt.setString(2, phno);
            stmt.executeUpdate();
        }
    }
    public void deleteTasksByEmployeeId(int employeeId) throws SQLException {
        String sql = "DELETE FROM task WHERE employee_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, employeeId);
            stmt.executeUpdate();
        }
    }
  
    public void deleteUserByPhno(String phno) throws SQLException {
        String getEmployeeIdSql = "SELECT id FROM employee WHERE phno = ?";
        String deleteEmployeeSql = "DELETE FROM employee WHERE phno = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement getEmployeeIdStmt = conn.prepareStatement(getEmployeeIdSql);
             PreparedStatement deleteEmployeeStmt = conn.prepareStatement(deleteEmployeeSql)) {

            // Start a transaction
            conn.setAutoCommit(false);

            // Get the employee ID
            getEmployeeIdStmt.setString(1, phno);
            ResultSet rs = getEmployeeIdStmt.executeQuery();
            if (rs.next()) {
                int employeeId = rs.getInt("id");

                // Delete related tasks
                deleteTasksByEmployeeId(employeeId);

                // Delete the employee
                deleteEmployeeStmt.setString(1, phno);
                deleteEmployeeStmt.executeUpdate();

                // Commit the transaction
                conn.commit();
            } else {
                throw new SQLException("Employee not found with phone number: " + phno);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Rethrow the exception after logging it
        }
    }

}
