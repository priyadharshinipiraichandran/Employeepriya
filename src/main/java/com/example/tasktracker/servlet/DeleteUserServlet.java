package com.example.tasktracker.servlet;

import com.example.tasktracker.dao.UserDao;

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

@WebServlet("/empdelete")
public class DeleteUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDao userDao = new UserDao(); // Replace with your UserDao instance
    private static final Logger LOGGER = Logger.getLogger(DeleteUserServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Get existing session without creating new

        if (session != null) {
            String phno = (String) session.getAttribute("phno");
            try {
                userDao.deleteUserByPhno(phno);
                session.invalidate(); // Invalidate the session after deletion
                response.sendRedirect("user_deleted.jsp"); // Redirect to a confirmation page
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error deleting user", e);
                request.setAttribute("message", "Error deleting user: " + e.getMessage());
                request.getRequestDispatcher("error.jsp").forward(request, response); // Forward to an error page
            }
        } else {
            // Handle if session is not found or expired
            request.setAttribute("message", "Session expired or user not logged in");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
