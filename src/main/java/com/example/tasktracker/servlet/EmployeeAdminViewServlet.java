package com.example.tasktracker.servlet;

import com.example.tasktracker.dao.TaskDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@WebServlet("/empadminview")
public class EmployeeAdminViewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TaskDao taskDao = new TaskDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String phonenumber = request.getParameter("phno"); // Get phone number from request parameter
        String dateStr = request.getParameter("date");

        // Parse the date string to LocalDate
        LocalDate date;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Adjust the pattern if needed
            date = LocalDate.parse(dateStr, formatter);
        } catch (Exception e) {
            request.setAttribute("message", "Invalid date format.");
            request.getRequestDispatcher("weekerror.jsp").forward(request, response);
            return;
        }

        try {
            // Fetch task durations for the employee
            Map<String, Long> taskDurations = taskDao.getTaskDurationsByPhonenumberAndDate(phonenumber, date);

            // Convert to JSON format for chart rendering
            String jsonData = convertToJSON(taskDurations);

            // Set attributes to be used in JSP
            request.setAttribute("taskData", jsonData);
            request.setAttribute("phno", phonenumber);
            request.setAttribute("date", date);
            request.getRequestDispatcher("admin_task_chart.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("message", "Session expired or user not logged in");
            request.getRequestDispatcher("weekerror.jsp").forward(request, response);
        }
    }

    private String convertToJSON(Map<String, Long> taskDurations) {
        StringBuilder jsonData = new StringBuilder("[");
        boolean first = true;
        for (Map.Entry<String, Long> entry : taskDurations.entrySet()) {
            if (!first) {
                jsonData.append(",");
            }
            jsonData.append("{");
            jsonData.append("\"task\": \"").append(entry.getKey()).append("\",");
            jsonData.append("\"duration\": ").append(entry.getValue());
            jsonData.append("}");
            first = false;
        }
        jsonData.append("]");
        return jsonData.toString();
    }
}
