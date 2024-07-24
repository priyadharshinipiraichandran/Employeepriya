package com.example.tasktracker.servlet;

import com.example.tasktracker.dao.TaskDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.time.LocalDate;

@WebServlet("/taskchart")
public class TaskChartServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TaskDao taskDao = new TaskDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String phonenumber = (String) request.getSession().getAttribute("phno"); // Assuming phno is stored in session
        LocalDate date = LocalDate.parse(request.getParameter("datelabel"));

        try {
            // Fetch task durations for the employee
            Map<String, Long> taskDurations = taskDao.getTaskDurationsByPhonenumberAndDate(phonenumber, date);

            // Convert to JSON format for chart rendering
            String jsonData = convertToJSON(taskDurations);

            // Set attributes to be used in JSP
            request.setAttribute("taskData", jsonData);
            request.setAttribute("employeeId", phonenumber); // Set employee ID assuming phonenumber is the ID
            request.setAttribute("date", date);
            request.getRequestDispatcher("taskchart.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error fetching task durations", e);
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
