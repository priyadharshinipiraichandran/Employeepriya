package com.example.tasktracker.servlet;

import com.example.tasktracker.dao.TaskDao;
import com.example.tasktracker.model.Task;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

@WebServlet("/task")
public class TaskServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TaskDao taskDao = new TaskDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String phonenumber = (String) session.getAttribute("phno");

        if (phonenumber != null && !phonenumber.isEmpty()) {
            // Process other parameters
            String project = request.getParameter("project");
            LocalDate date = LocalDate.now();
            LocalTime startTime = LocalTime.now();
            String category = request.getParameter("category");
            String description = request.getParameter("description");

            try {
                int employeeId = taskDao.getEmployeeIdByPhonenumber(phonenumber);

                // Save task
                Task task = new Task(employeeId, project, date, startTime, null, category, description);
                taskDao.saveTask(task);
                response.sendRedirect("success.jsp");
            } catch (SQLException e) {
                request.setAttribute("message", "Error occurred while saving the task.");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("message", "Session expired or user not logged in.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
