package com.example.tasktracker.dao;

import com.example.tasktracker.model.Employee;
import com.example.tasktracker.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDao {

    public Employee getEmployeeByUsername(String username) throws SQLException {
        String query = "SELECT * FROM employee WHERE username = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getInt("id"));
                employee.setphonenumber(rs.getString("phno"));
                employee.setRole(rs.getString("role"));
                employee.setUsername(rs.getString("username"));
                employee.setPassword(rs.getString("password"));
                return employee;
            }
        }
        return null;
    }

    public Employee getEmployeeById(int id) throws SQLException {
        String query = "SELECT * FROM employee WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getInt("id"));
                employee.setphonenumber(rs.getString("phno"));
                employee.setRole(rs.getString("role"));
                employee.setUsername(rs.getString("username"));
                employee.setPassword(rs.getString("password"));
                return employee;
            }
        }
        return null;
    }

    public void saveEmployee(Employee employee) throws SQLException {
        String query = "INSERT INTO employee (phno, role, username, password) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, employee.getphonenumber());
            stmt.setString(2, employee.getRole());
            stmt.setString(3, employee.getUsername());
            stmt.setString(4, employee.getPassword());
            stmt.executeUpdate();
        }
    }

    public void updateEmployee(Employee employee) throws SQLException {
        String query = "UPDATE employee SET phno = ?, password = ? WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, employee.getphonenumber());
            stmt.setString(2, employee.getPassword());
            stmt.setInt(3, employee.getId());
            stmt.executeUpdate();
        }
    }
    public void deleteEmployeeByPhno(String phno) throws SQLException {
        String sql = "DELETE FROM employee WHERE phno = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, phno);
            stmt.executeUpdate();
        }
    }
}
