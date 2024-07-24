package com.example.tasktracker.servlet;

import com.example.tasktracker.dao.EmployeeDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/adminempdelete")
public class AdminDeleteEmployeeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private EmployeeDao employeeDao = new EmployeeDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String phno = request.getParameter("newpass"); // Assuming newpass is used to enter phone number

        try {
            employeeDao.deleteEmployeeByPhno(phno);
            response.sendRedirect("admin_delete_success.jsp"); // Redirect to a success page
        } catch (SQLException e) {
            request.setAttribute("message", "Error deleting employee: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response); // Forward to an error page
        }
    }
}
