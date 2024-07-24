// DatabaseUtil.java
package com.example.tasktracker.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/tasktracker?useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "rajpandi007";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
