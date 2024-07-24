<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error Page</title>
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background: url('https://example.com/high-definition-error-background.jpg') no-repeat center center fixed;
            background-size: cover;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            color: #fff;
            text-shadow: 2px 2px 4px #000;
        }

        .error-container {
            background-color: rgba(0, 0, 0, 0.7);
            padding: 40px;
            border-radius: 15px;
            box-shadow: 0 10px 20px rgba(0, 0, 0, 0.5);
            text-align: center;
            width: 500px;
        }

        .error-container h1 {
            margin-bottom: 30px;
            font-size: 2.5em;
            color: #ff5252;
        }

        .error-container p {
            margin-bottom: 20px;
            font-size: 1.2em;
        }

        .error-container a {
            color: #2196F3;
            text-decoration: none;
            font-weight: bold;
            transition: color 0.3s;
        }

        .error-container a:hover {
            color: #ff5252;
        }
    </style>
    <script>
        document.addEventListener("DOMContentLoaded", function() {
            const homeLink = document.querySelector(".error-container a");

            homeLink.addEventListener("mouseover", function() {
                this.style.color = "#ff5252";
            });

            homeLink.addEventListener("mouseout", function() {
                this.style.color = "#2196F3";
            });
        });
    </script>
</head>
<body>
    <div class="error-container">
        <h1>Oops! Something went wrong.</h1>
        <p>${exception}</p>
        <p>Please <a href="login.jsp">click here</a> to go back to the home page.</p>
    </div>
</body>
</html>
