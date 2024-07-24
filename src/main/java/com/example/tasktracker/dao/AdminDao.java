package com.example.tasktracker.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.tasktracker.util.DatabaseUtil;

public class AdminDao {
    // Method to validate admin login credentials
    public boolean validateAdmin(String adminId, String adminPass) throws SQLException {
        String sql = "SELECT * FROM admin WHERE admin_id = ? AND admin_pass = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, adminId);
            stmt.setString(2, adminPass);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // true if match found, false otherwise
        }
    }
}
