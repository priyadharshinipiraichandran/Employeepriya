package com.example.tasktracker.servlet;

import com.example.tasktracker.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/updatepassemployee")
public class UpdatePasswordServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserDao userDao = new UserDao(); // Replace with your UserDao instance

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession(false); // Get existing session without creating new

        if (session != null) {
            String phno = (String) session.getAttribute("phno");
            String oldPassword = request.getParameter("userpass");
            String newPassword = request.getParameter("newpass");
            String confirmPassword = request.getParameter("renewpass");

            if (newPassword.equals(confirmPassword)) {
                try {
                    // Check if old password matches the current password in the database
                    if (userDao.checkPasswordByPhno(phno, oldPassword)) {
                        // Update the password
                        userDao.updatePasswordByPhno(phno, newPassword);
                        response.sendRedirect("password_updated.jsp"); // Redirect to success page
                    } else {
                        request.setAttribute("message", "Incorrect old password");
                        request.getRequestDispatcher("update_password.jsp").forward(request, response);
                    }
                } catch (Exception e) {
                    request.setAttribute("message", "Error updating password: " + e.getMessage());
                    request.getRequestDispatcher("update_password.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("message", "Passwords do not match");
                request.getRequestDispatcher("update_password.jsp").forward(request, response);
            }
        } else {
            // Handle if session is not found or expired
            request.setAttribute("message", "Session expired or user not logged in");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}