<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Weekly View</title>
    <!-- Include Chart.js library -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .container {
            width: 80%;
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            max-width: 1200px; /* Limit the maximum width */
            box-sizing: border-box; /* Ensure padding is included in width */
        }
        h2, h3 {
            text-align: center;
            color: #333;
            margin: 0;
        }
        h2 {
            margin-bottom: 20px;
        }
        h3 {
            margin-bottom: 10px;
            font-size: 16px;
        }
        #taskChart {
            margin: 20px auto;
            display: block;
            width: 100%;
            max-height: 400px; /* Limit the height */
        }
        p {
            text-align: center;
            font-size: 16px;
            color: #666;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Weekly Task Duration Chart</h2>
        <h3>Employee ID: <%= request.getAttribute("phonenumber") %></h3>
        <h3>Date Range: <%= request.getAttribute("startDate") %> to <%= request.getAttribute("endDate") %></h3>
        <canvas id="taskChart"></canvas>
        <p>Total Duration: <%= request.getAttribute("totalDuration") %> Hours</p>
    </div>

    <script>
        // Retrieve data from servlet attributes
        var dates = <%= request.getAttribute("dates") %>;
        var durations = <%= request.getAttribute("durations") %>;

        // Chart.js line chart configuration
        var ctx = document.getElementById('taskChart').getContext('2d');
        var taskChart = new Chart(ctx, {
            type: 'line',
            data: {
                labels: dates,
                datasets: [{
                    label: 'Task Durations (Hours)',
                    data: durations,
                    backgroundColor: 'rgba(54, 162, 235, 0.2)',
                    borderColor: 'rgba(54, 162, 235, 1)',
                    borderWidth: 2,
                    fill: true // Fill the area under the line
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                scales: {
                    x: {
                        ticks: {
                            autoSkip: true,
                            maxRotation: 45,
                            minRotation: 0
                        },
                        title: {
                            display: true,
                            text: 'Dates'
                        }
                    },
                    y: {
                        beginAtZero: false,
                        title: {
                            display: true,
                            text: 'Duration (Hours)'
                        }
                    }
                }
            }
        });
    </script>
</body>
</html>
