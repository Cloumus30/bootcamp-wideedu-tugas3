<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Products Cart</title>
</head>

<style type="text/css">
	body{
		margin-left: 8em;
		margin-right: 8em;
		margin-top: 2em;
	}
	
	.flex{
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
		border-collapse: collapse;
	}
	tr.no-border td {
	  	border: 0;
	}
</style>

<body>
	<h1 align="center">Order Cart</h1>
	
	<div style="margin-top=25px;">
		<div>
			<p>Customer: <c:out value="${sessionScope.customer_name}"></c:out></p>
			<p>Address: <c:out value="${sessionScope.address }"></c:out> </p>
		</div>
	</div>
	
	<div>
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
					Qty
				</th>
				<th>
					Total
				</th>
			</tr>
			<c:forEach items="${orderItems}" var="orderItem">
				<tr>
					<td>
						<c:out value="1"></c:out>
					</td>
					<td>
						<c:out value="${orderItem.value.productName}"></c:out>
					</td>
					<td>
						<c:out value="${orderItem.value.type}"></c:out>
					</td>
					<td class="numberFormated">
						<c:out value="${orderItem.value.price}"></c:out>
					</td>
					<td>
						<c:out value="${orderItem.value.quantity}"></c:out>
					</td>
					<td class="numberFormated">
						<c:out value="${orderItem.value.quantity * orderItem.value.price}"></c:out>
					</td>
				</tr>
			</c:forEach>
				<tr style="border:0 transparent;">
					<td colspan="3"></td>
					<td style="padding-top:20px; font-size: large;" align="center">
						Total
					</td>
					<td style="padding-top:20px; font-size: large;" align="center">
						:
					</td>
					<td style="padding-top:20px; font-size: large;" class="numberFormated">
						<c:out value="${totalPrice}"></c:out>
					</td>
				</tr>
				<tr style="border:0 transparent;">
					<td colspan="3"></td>
					<td colspan="2">
					</td>
					<td style="padding-top:20px; font-size: large;">
						<button type="button" onclick="placeOrderHandle()" style="width:100%; font-weight: bolder;">Place Order</button>
					</td>
				</tr>
		</table>
		
		<form id="form-order" action="product-list?action=place-order" method="POST">
		
		</form>
	</div>
	
	<script type="text/javascript">
	// Formate Number with thousan separator
		window.addEventListener('load', function () {
		  const numberFormateds = document.getElementsByClassName("numberFormated");
		  
		  for (let el of numberFormateds) {
		      el.innerHTML = Intl.NumberFormat().format(el.innerText);
		  }
		})
		
		function placeOrderHandle(){
			const form = document.getElementById("form-order");
			form.submit();
		}
	</script>
</body>
</html>