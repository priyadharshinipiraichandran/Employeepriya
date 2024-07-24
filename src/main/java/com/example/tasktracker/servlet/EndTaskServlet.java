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
import java.time.LocalTime;

@WebServlet("/endTask")
public class EndTaskServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TaskDao taskDao = new TaskDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String phonenumber = (String) session.getAttribute("phno");

        System.out.println("EndTaskServlet: Received request to end task");
        System.out.println("EndTaskServlet: Phone number from session = " + phonenumber);

        if (phonenumber != null && !phonenumber.isEmpty()) {
            try {
                int employeeId = taskDao.getEmployeeIdByPhonenumber(phonenumber);
                System.out.println("EndTaskServlet: Retrieved Employee ID = " + employeeId);

                String taskIdStr = request.getParameter("taskId");
                System.out.println("EndTaskServlet: Task ID from request = " + taskIdStr);

                int taskId = Integer.parseInt(taskIdStr);
                System.out.println("EndTaskServlet: Parsed Task ID = " + taskId);

                // End the task by setting the end time
                LocalTime endTime = LocalTime.now();
                System.out.println("EndTaskServlet: Current time to set as end time = " + endTime);

                taskDao.endCurrentTask(employeeId, endTime);
                System.out.println("EndTaskServlet: Task ended successfully");

                // Redirect back to the ongoing tasks page
                response.sendRedirect("ongoingTasks");
            } catch (SQLException e) {
                System.out.println("EndTaskServlet: SQLException - Failed to end the task");
                e.printStackTrace();

                request.setAttribute("message", "Failed to end the task.");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                System.out.println("EndTaskServlet: NumberFormatException - Invalid Task ID format");
                e.printStackTrace();

                request.setAttribute("message", "Invalid Task ID format.");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        } else {
            System.out.println("EndTaskServlet: Session expired or user not logged in");
            request.setAttribute("message", "Session expired or user not logged in.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
