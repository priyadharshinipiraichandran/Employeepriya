package com.example.tasktracker.servlet;

import com.example.tasktracker.dao.AdminDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/empadminlogin")
public class AdminLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AdminDao adminDao = new AdminDao(); // Replace with your AdminDao instance
    private static final Logger LOGGER = Logger.getLogger(AdminLoginServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String adminId = request.getParameter("adminid");
        String adminPass = request.getParameter("adminpass");

        try {
            if (adminDao.validateAdmin(adminId, adminPass)) {
                HttpSession session = request.getSession();
                session.setAttribute("adminId", adminId);
                response.sendRedirect("admin_dashboard.jsp"); // Redirect to admin dashboard
            } else {
                request.setAttribute("status", "Invalid admin ID or password");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error during admin login", e);
            request.setAttribute("status", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
