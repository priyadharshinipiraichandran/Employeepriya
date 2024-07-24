<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Login</title>
</head>
<body>
	<!DOCTYPE html>
	<html lang="en">
	<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<title>Eren Bank Login Page</title>
	
	<!-- Font Icon -->
	<link rel="stylesheet"
		href="fonts/material-icon/css/material-design-iconic-font.min.css">
	
	<!-- Main css -->
	<link rel="stylesheet" href="css/style.css">
	</head>
	<body>
	<input type = "hidden" id="status" value="<%= request.getAttribute("status") %>">
		<div class="main">
			<section class="sign-in">
				<div class="container">
					<div class="signin-content">
						<div class="signin-form">
							<h2 class="form-title"> Admin Log in</h2>
							
							<form method="post" action="empadminlogin" class="register-form"
								id="login-form">
								<div class="form-group">
									<label for="adminid"><i
										class="zmdi zmdi-account material-icons-name"></i></label> <input
										type="text" name="adminid" id="adminid"
										placeholder="Admin ID" />
								</div>
								<div class="form-group">
									<label for="adminpass"><i class="zmdi zmdi-lock"></i></label> <input
										type="password" name="adminpass" id="adminpass"
										placeholder="Admin Password" />
								</div>
								<div class="form-group form-button">
									<input type="submit" name="signin" id="signin"
										class="form-submit" value="Log in" />
								</div>
							</form>
</body>
</html>