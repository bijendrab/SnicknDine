<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ page isELIgnored="false"%>

<%@ include file="navbar.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Products</title>
<link rel="icon" type="image/x-icon"
	href="<c:url value="../resource/images/favicon1.png"/>" />
<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
<script src="<c:url value="../resource/js/productController.js"/>"></script>
</head>
<body>
	<div ng-app="myapp">
		<div class="container" style="width: 829px">
			<h2>Item Details</h2>
			<p>Details of the Item</p>
			<table class="table table-bordered" id="prod">
				<tbody>

					<tr>
						<td>Prodcut Image</td>
						<td><img
							src="<c:url value="/resource/images/${productObj.productId}.jpg"/>"
							width="40%" alt="${productObj.productName}" /></td>
					</tr>
					<tr>
						<td>Item ID</td>
						<td>${productObj.productId }</td>
					</tr>
					<tr>
						<td>Item Name</td>
						<td>${productObj.productName }</td>
					</tr>
					<tr>
						<td>Item Type</td>
						<td>${productObj.productType }</td>
					</tr>
					<tr>
						<td>Item Cuisine</td>
						<td>${productObj.productCuisine }</td>
					</tr>
					<tr>
						<td>Item Category</td>
						<td>${productObj.productCategory}</td>
					</tr>
					<tr>
						<td>Item Description</td>
						<td>${productObj.productDescription}</td>
					</tr>
					<tr>
						<td>Item Price</td>
						<td>${productObj.productPrice}</td>
					</tr>
					<tr>
						<td>Stock Available</td>
						<td>${productObj.unitStock}</td>
					</tr>
					<tr>
						<td>Add to Cart:</td>
						<td><c:url value="/cart/add/${productObj.productId}"
								var="addcart"></c:url>
							<div ng-controller="myController">
								<security:authorize access="hasRole('ROLE_USER')">
									<a href="#" ng-click="addToCart(${productObj.productId})"
										class="btn btn-info"
										style="margin-top: 0px; width: 150px; float: left; margin-right: 31px;">
										<span class="glyphicon glyphicon-shopping-cart"></span>
									</a>
								</security:authorize>
								<a href="<c:url value="/getAllProducts" />" class="btn btn-info"
									style="margin-top: 0px; width: 150px; float: right; margin-right: 31px;">
									<span class="glyphicon glyphicon-arrow-left"></span>
								</a>
							</div></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>
<%@ include file="footer.jsp"%>
