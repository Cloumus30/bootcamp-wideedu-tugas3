<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Product View</title>
</head>
<body>
	<h1 align="center">Product View</h1>
	
	<div align="left" style="display:flex; justify-content: center;">
		<form action="products" method="post">
			<input type="hidden" name="is_update" value="1">
			<input type="hidden" name="product_id" value="${product.id}">
			<div style="margin-bottom: 20px;">
				<label for="name">Name: </label>
				<input type="text" id="name" name="name" value="${product.name}" required>
			</div>
		
			<div style="margin-bottom: 20px;">
				<label for="type">Type: </label>
				<select id="type" required name="type_id">
					<c:forEach items="${types}" var="type">
						<c:if test="${type.id == product.type.id}">
							<option value="${type.id }" selected> <c:out value="${type.name}"></c:out> </option>	
						</c:if>
						<c:if test="${type.id != product.type.id}">
							<option value="${type.id }"> <c:out value="${type.name}"></c:out> </option>	
						</c:if>
					</c:forEach>
				</select>
			</div>
			
			<div style="margin-bottom: 20px;">
				<label for="price">Price</label>
				<input type="number" id="price" name="price" value="${product.price}" required>
			</div>
			
			<div align="center">
				<button>Update</button>
			</div>
		</form>
	</div>
	
</body>
</html>