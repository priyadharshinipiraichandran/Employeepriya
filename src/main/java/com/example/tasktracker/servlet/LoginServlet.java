package com.example.tasktracker.servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	String phonenumber = request.getParameter("phno");
        String password = request.getParameter("password");

        boolean isAuthenticated = authenticateUser(phonenumber, password);

        if (isAuthenticated) {
            HttpSession session = request.getSession();
            session.setAttribute("phno", phonenumber);
            response.sendRedirect("taskform.jsp");
        } else {
            response.sendRedirect("error.jsp");
        }
    }

    private boolean authenticateUser(String phonenumber, String password) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/tasktracker?useSSL=false";
        String jdbcUser = "root";
        String jdbcPassword = "rajpandi007";

        String sql = "SELECT * FROM employee WHERE phno = ? AND password = ?";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, phonenumber);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
    
        