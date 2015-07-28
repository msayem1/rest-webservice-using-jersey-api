<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
	"http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>REST web service using Jersey API</title>
	</head>
	<body>	
		<div align="left">
			<h2>REST web service using Jersey API</h2>
			<h3>A RESTful web service implementation using Jersey (JAX-RS) API.</h3>
			<form action="http://localhost:8080/rest-webservice-using-jersey-api/rest/create" method="POST">
				<p>Userid: <input type="text" id="userid" name="userid" /></p>
				<p>Username: <input type="text" id="username" name="username" /></p>
				<p>Password: <input type="text" id="password" name="password" /></p>
				<p>Email: <input type="text" id="email" name="email" /></p>
				<input type="submit" value="Save to Database" />
			</form><br><br>
			<a href='http://localhost:8080/rest-webservice-using-jersey-api/rest/read' >Click here</a>
			to see the list of users.
		</div>
	</body>
</html>