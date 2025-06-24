<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>List Of Our Products</title>

<style type="text/css">
body{
		margin-left: 10em;
		margin-right: 10em;
		margin-top: 2em;
	}
	.flex{
		display: flex;
		gap:15px;
		margin-top:10px;
	}
	.flex-wrap{
		flex-wrap: wrap;
	}
	.justify-center{
		justify-content: center;
	}
	.border-product{
		border: 1px solid black;
		padding: 10px 10px 10px 10px;
	}
</style>

</head>
<body>
	<h1 align="center">Product List</h1>
	<a href="product-list?action=cart" style="margin-left:5rem; margin-bottom:20px;">
		<button>Cart</button>
	</a>
	<div class="flex flex-wrap justify-center" >
		<c:forEach items="${products}" var="product">
			<div class="border-product" style="width:20rem;">
				<form id="form-product${product.id}">
					<input type="hidden" readonly value="${product.id}" name="product_id">
					<div style="margin-bottom: 8px;">
						<label >Name: </label>
						<input type="text" readonly value="${product.name}" name="name">
					</div>
					<div style="margin-bottom: 8px;">
						<label >Type: </label>
						<select name="type" disabled>
							<option selected value="${product.type.id}">${product.type.name}</option>
						</select>
						<input type="hidden" readonly value="${product.type.name}" name="type">
					</div>
					<div style="margin-bottom: 20px;">
						<label >Price: </label>
						<fmt:formatNumber value="${product.price}" pattern="#,###.##" var="pat" /> 
						<input class="numberFormated" type="text" value="${pat}" readonly>
						<input class="numberFormated" type="number" name="price" value="${product.price}" hidden>
					</div>
					
					<div align="right" style="margin-bottom: 10px;">
						<label >Quantity: </label>
						<input type="number" name="quantity">
					</div>
					
					<div align="right">
						<button type="button" onclick="addToCartHandle(${product.id})">Add To Cart</button>
					</div>
				</form>
			</div>
		</c:forEach>		
	</div>
	
	<script>
		// Formate Number with thousan separator
		
		async function addToCartHandle(formId){
			const form = document.getElementById("form-product"+formId);
			const formData = new FormData(form);
			// Validate Quantity
			const quantity = Number(formData.get("quantity"));
			if(quantity < 1){
				alert("Quantity Value must be more than 0");
			}else{
				const jsonForm = JSON.stringify(Object.fromEntries(formData));
				const res = await fetch("product-list?action=order-item-session", {
					method: "POST",
					headers: {
					    "Content-Type": "application/json",
					  },
					body: jsonForm,
				})
				if(res.status == 200){
					alert("Success add Product to Cart");
				}
			}
		}
	</script>
</body>
</html>