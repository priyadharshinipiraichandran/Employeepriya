package com.example.tasktracker.servlet;

import com.example.tasktracker.dao.EmployeeDao;
import com.example.tasktracker.model.Employee;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/empadminupdate")
public class UpdateEmployeeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private EmployeeDao employeeDao = new EmployeeDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int empId = Integer.parseInt(request.getParameter("accno"));
        String newPhno = request.getParameter("newphno");
        String newPass = request.getParameter("newpass");

        try {
            Employee existingEmployee = employeeDao.getEmployeeById(empId);
            if (existingEmployee == null) {
                request.setAttribute("message", "Employee not found");
                request.getRequestDispatcher("admin_update.jsp").forward(request, response);
                return;
            }

            if (newPhno == null || newPhno.isEmpty()) {
                newPhno = existingEmployee.getphonenumber();
            }
            if (newPass == null || newPass.isEmpty()) {
                newPass = existingEmployee.getPassword();
            }

            existingEmployee.setphonenumber(newPhno);
            existingEmployee.setPassword(newPass);

            employeeDao.updateEmployee(existingEmployee);
            response.sendRedirect("update_success.jsp"); // Redirect to a success page
        } catch (SQLException e) {
            request.setAttribute("message", "Error updating employee: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
