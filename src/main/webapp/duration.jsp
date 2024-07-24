<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form id="taskDurationForm" action="taskDuration" method="post">
    <div class="form-floating mb-3">
        <input class="form-control" id="username" type="text" name="username" placeholder="Enter your username..." required>
        <label for="username">Username</label>
        <div class="invalid-feedback">Username is required.</div>
    </div>
    <div class="form-floating mb-3">
        <input class="form-control" id="password" type="password" name="password" placeholder="Enter your password..." required>
        <label for="password">Password</label>
        <div class="invalid-feedback">Password is required.</div>
    </div>
    <div class="form-floating mb-3">
        <input class="form-control" id="date" type="date" name="date" required>
        <label for="date">Date</label>
        <div class="invalid-feedback">Date is required.</div>
    </div>
    <div class="form-group form-button">
        <input type="submit" class="btn btn-primary" value="Submit">
    </div>
</form>
	
</body>
</html>