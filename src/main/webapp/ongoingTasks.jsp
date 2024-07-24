<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.example.tasktracker.model.Task" %>
<!DOCTYPE html>
<html>
<head>
    <title>Ongoing Tasks</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f4f4f4;
        }

        h2 {
            color: #333;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
            background-color: #fff;
        }

        th, td {
            padding: 10px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #f2f2f2;
            color: #333;
        }

        tr:hover {
            background-color: #f5f5f5;
        }

        form {
            display: inline;
        }

        input[type="submit"] {
            padding: 5px 10px;
            border: none;
            border-radius: 4px;
            background-color: #4CAF50;
            color: white;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #45a049;
        }

        p {
            color: #666;
        }
    </style>
</head>
<body>
    <h2>Ongoing Tasks</h2>

    <%
        List<Task> ongoingTasks = (List<Task>) request.getAttribute("ongoingTasks");

        if (ongoingTasks != null && !ongoingTasks.isEmpty()) {
    %>
        <table>
            <thead>
                <tr>
                    <th>Task ID</th>
                    <th>Project</th>
                    <th>Start Time</th>
                    <th>Category</th>
                    <th>Description</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <%
                    for (Task task : ongoingTasks) {
                %>
                    <tr>
                        <td><%= task.getId() %></td>
                        <td><%= task.getProject() %></td>
                        <td><%= task.getStartTime() %></td>
                        <td><%= task.getCategory() %></td>
                        <td><%= task.getDescription() %></td>
                        <td>
                            <form method="post" action="endTask">
                                <input type="hidden" name="taskId" value="<%= task.getId() %>" />
                                <input type="submit" value="End Task" />
                            </form>
                        </td>
                    </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    <%
        } else {
    %>
        <p>No ongoing tasks available.</p>
    <%
        }
    %>
</body>
</html>
