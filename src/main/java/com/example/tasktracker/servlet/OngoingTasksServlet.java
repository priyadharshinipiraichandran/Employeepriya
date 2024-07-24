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
import java.util.List;

@WebServlet("/ongoingTasks")
public class OngoingTasksServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TaskDao taskDao = new TaskDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String phonenumber = (String) session.getAttribute("phno");

        System.out.println("OngoingTasksServlet: Received request to retrieve ongoing tasks");
        System.out.println("OngoingTasksServlet: Phone number from session = " + phonenumber);

        if (phonenumber != null && !phonenumber.isEmpty()) {
            try {
                int employeeId = taskDao.getEmployeeIdByPhonenumber(phonenumber);
                System.out.println("OngoingTasksServlet: Retrieved Employee ID = " + employeeId);

                List<Task> ongoingTasks = taskDao.getOngoingTasks(employeeId);
                System.out.println("OngoingTasksServlet: Retrieved " + ongoingTasks.size() + " ongoing tasks");

                for (Task task : ongoingTasks) {
                    System.out.println("OngoingTasksServlet: Task ID = " + task.getId() + ", Project = " + task.getProject());
                }

                request.setAttribute("ongoingTasks", ongoingTasks);
                request.getRequestDispatcher("ongoingTasks.jsp").forward(request, response);
            } catch (SQLException e) {
                System.out.println("OngoingTasksServlet: SQLException - Failed to retrieve ongoing tasks");
                e.printStackTrace();

                request.setAttribute("message", "Failed to retrieve ongoing tasks.");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        } else {
            System.out.println("OngoingTasksServlet: Session expired or user not logged in");
            request.setAttribute("message", "Session expired or user not logged in.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
