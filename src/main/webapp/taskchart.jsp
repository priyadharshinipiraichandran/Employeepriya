<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Task Durations Chart</title>
    <!-- Include Chart.js library -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            text-align: center;
        }
        h1 {
            margin-bottom: 20px;
        }
        #taskChart {
            max-width: 80%; /* Adjust max-width as needed */
            margin: 0 auto; /* Center the chart horizontally */
            display: block;
        }
    </style>
</head>
<body>
    <h1>Task Durations Chart - Phone number: ${employeeId}, Date: ${date}</h1>
    <canvas id="taskChart" width="300" height="300"></canvas>
    <p>Total duration worked today: <span id="totalDuration"></span> hours/ 8 hours</p>

    <script>
        // Retrieve data passed from servlet
        var taskData = ${taskData};

        // Prepare data for Chart.js
        var labels = [];
        var durations = [];
        var totalDuration = 0; // Variable to calculate total duration

        taskData.forEach(function(task) {
            labels.push(task.task);
            var durationInMinutes = task.duration / 3600;
            durations.push(durationInMinutes);
            totalDuration += durationInMinutes; // Accumulate total duration
        });

        // Render pie chart
        var ctx = document.getElementById('taskChart').getContext('2d');
        var myPieChart = new Chart(ctx, {
            type: 'pie',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Task Durations',
                    data: durations,
                    backgroundColor: [
                        'rgba(255, 99, 132, 0.5)',
                        'rgba(54, 162, 235, 0.5)',
                        'rgba(255, 206, 86, 0.5)',
                        'rgba(75, 192, 192, 0.5)',
                        'rgba(153, 102, 255, 0.5)',
                        'rgba(255, 159, 64, 0.5)'
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
                plugins: {
                    legend: {
                        position: 'top',
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

        // Display total duration worked today
        document.getElementById('totalDuration').textContent = totalDuration.toFixed(2);
    </script>
</body>
</html>
