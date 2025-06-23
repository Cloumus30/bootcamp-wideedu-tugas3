<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login Page</title>
</head>
<body>
	<h1>Login Page</h1>
	<h3> <c:out value="${login_message}"></c:out> </h3>
	
	<form action="login" method="post">
		<label for="username">Username</label>
		<input type="text" id="username" name="username">
		<br>
		<label for="password">Password</label>
		<input type="password" id="password" name="password">
		<br> <hr>
		<button type="submit">
			Submit
		</button>
	</form>
	
</body>
</html>