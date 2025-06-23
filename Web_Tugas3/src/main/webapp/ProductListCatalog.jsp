<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>List Product</title>

<style type="text/css">
	body{
		margin-left: 8em;
		margin-right: 8em;
		margin-top: 2em;
	}
	
	.flex-container{
		display: flex;
	}
	.justify-content-between{
		justify-content: space-between;
	}
	.justify-content-around{
		justify-content: space-around;
	}
	.underline{
		text-decoration: underline;
	}
	
	table{
		margin-top:2em;
		width: 100%;
	}
</style>

</head>
<body>
	<h1 align="center"> Katalog Product </h1>
	<div class="flex-container justify-content-between">
		
			<a href="products?action=form-add">
				<button class="underline"> Add Product</button>		
			</a>
		
		<a href="products?action=csv">
			<button class="underline"> Download CSV</button>		
		</a>
	</div>
	
	<table border="1">
		<tr>
			<th>
				No
			</th>
			<th>
				Product Name
			</th>
			<th>
				Type
			</th>
			<th>
				Price
			</th>
			<th>
				Action
			</th>
		</tr>
		<%int count = 1; %>
		<c:forEach items="${products}" var="product" varStatus="loop">
			<tr>
				<th>
					<c:out value="${loop.count}"></c:out>
				</th>
				<th>
					<c:out value="${product.name}"></c:out>
				</th>
				<th>
					<c:out value="${product.type.name}"></c:out>
				</th>
				<th>
					<c:out value="${product.price}"></c:out>
				</th>
				<th class="flex-container justify-content-around">
					<a href="products?action=form-view&product=${product.id}">View</a>
					<form id="form-delete${product.id}" action="products" method="POST" enctype="application/json">
						<input name="is_delete" value="1" hidden>
						<input name="product_id" value="${product.id}" hidden>
					</form>
					<a href="#" onclick="deleteProduct(${product.id})">Delete</a>
				</th>
			</tr>	
		<%count++; %>
		</c:forEach>
	</table>
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

	<script type="text/javascript">
		function deleteProduct(id){
			const confirmed = confirm("Want To Delete Product? ");
			if(confirmed){
				
				const form = document.getElementById("form-delete"+id);
				form.submit();
			}
		}
	</script>
</body>
</html>