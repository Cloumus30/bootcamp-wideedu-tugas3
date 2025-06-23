<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Product</title>
</head>
<body>
	<h1 align="center">Product Form</h1>
	
	<div align="left" style="display:flex; justify-content: center;">
		<form action="products" method="post">
			<div style="margin-bottom: 20px;">
				<label for="name">Name: </label>
				<input type="text" id="name" name="name" required>
			</div>
		
			<div style="margin-bottom: 20px;">
				<label for="type">Type: </label>
				<select id="type" required name="type_id">
					<c:forEach items="${types}" var="type">
						<option value="${type.id }"> <c:out value="${type.name}"></c:out> </option>
					</c:forEach>
				</select>
			</div>
			
			<div style="margin-bottom: 20px;">
				<label for="price">Price</label>
				<input type="number" id="price" name="price" required>
			</div>
			
			<div align="center">
				<button>Save</button>
			</div>
		</form>
	</div>
	
</body>
</html>