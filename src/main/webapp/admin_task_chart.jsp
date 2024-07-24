<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Task Chart</title>
    <!-- Include Chart.js library -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            margin: 0;
            padding: 0;
        }
        .container {
            width: 80%;
            margin: 20px auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h2 {
            text-align: center;
            margin-bottom: 20px;
            color: #333;
        }
        h3 {
            text-align: center;
            margin-bottom: 10px;
            color: #666;
        }
        #taskChart {
            margin: 0 auto;
            display: block;
            max-width: 100%;
            height: auto;
        }
        .total-duration {
            text-align: center;
            margin-top: 20px;
            font-size: 18px;
        }
        .no-task-message {
            text-align: center;
            color: red;
            font-size: 18px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Task Performance Chart</h2>
        <h3>Employee ID: <%= request.getAttribute("phno") %></h3>
        <h3>Total duration worked: <span id="totalDuration"></span> hours/ 8 hours</h3>
        <h3>Date: <%= request.getAttribute("date") %></h3>
        <canvas id="taskChart" width="400" height="400"></canvas>
        <div class="total-duration" id="totalDuration"></div>
        <div class="no-task-message" id="noTaskMessage"></div>
    </div>

    <script>
        // Retrieve task data JSON from servlet attribute
        var taskData = <%= request.getAttribute("taskData") %>;

        if (taskData && taskData.length > 0) {
            // Parse JSON data
            var tasks = [];
            var durations = [];
            var totalSeconds = 0;

            for (var i = 0; i < taskData.length; i++) {
                tasks.push(taskData[i].task);
                // Convert duration to minutes
                var durationInMinutes = taskData[i].duration / 3600; // Convert seconds to minutes
                durations.push(durationInMinutes);
                totalSeconds += taskData[i].duration;
            }

            // Calculate total duration in hours
            var totalHours = totalSeconds / 3600;

            // Chart.js pie chart configuration
            var ctx = document.getElementById('taskChart').getContext('2d');
            var taskChart = new Chart(ctx, {
                type: 'pie',
                data: {
                    labels: tasks,
                    datasets: [{
                        label: 'Task Durations',
                        data: durations,
                        backgroundColor: [
                            'rgba(255, 99, 132, 0.2)',
                            'rgba(54, 162, 235, 0.2)',
                            'rgba(255, 206, 86, 0.2)',
                            'rgba(75, 192, 192, 0.2)',
                            'rgba(153, 102, 255, 0.2)',
                            'rgba(255, 159, 64, 0.2)'
                        ],
                        borderColor: [
                            'rgba(255, 99, 132, 1)',
                            'rgba(54, 162, 235, 1)',
                            'rgba(255, 206, 86, 1)',
                            'rgba(75, 192, 192, 1)',
                            'rgba(153, 102, 255, 1)',
                            'rgba(255, 159, 64, 1)'
                        ],
                        borderWidth: 1
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: true,
                    plugins: {
                        legend: {
                            position: 'bottom',
                            labels: {
                                font: {
                                    size: 14,
                                    weight: 'bold'
                                }
                            }
                        },
                        tooltip: {
                            callbacks: {
                                label: function(tooltipItem) {
                                    return tooltipItem.label + ': ' + tooltipItem.raw.toFixed(2) + ' hours';
                                }
                            }
                        }
                    }
                }
            });

            // Display total duration and comparison
            document.getElementById('totalDuration').textContent = totalHours.toFixed(2);
        } else {
            // Display message when no tasks are available
            document.getElementById('noTaskMessage').innerHTML = 'No tasks available for this date.';
        }
    </script>
</body>
</html>
