<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit Product</title>
<link rel="icon" type="image/x-icon"
	href="<c:url value="../resource/images/favicon1.png"/>" />
<link rel="stylesheet"
	href="<c:url value="/resource/bootstrap/css/bootstrap.min.css"/>">
<script src="<c:url value="/resource/js/jquery.js"/>"></script>
<script src="<c:url value="/resource/bootstrap/js/bootstrap.min.js"/>"></script>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resource/css/register.css"/>">

</head>
<body>
	<%@ include file="navbar.jsp"%>
	<div class="container" style="margin-bottom: 19px">
		<h1 class="well">Edit Item !</h1>
		<div class="col-lg-12 well">
			<div class="row">

				<!--  RegisterServlet  form -->
				<c:url value="/admin/product/editProduct" var="url"></c:url>
				<form:form method="post" action="${url}"
					commandName="editProductObj">
					<div class="col-sm-12">
						<div class="row">
							<div class="col-sm-6 form-group">
								<form:label path="productId">Product Id</form:label>
								<form:input type="text" placeholder="Enter ProductId.."
									class="form-control" path="productId" disabled="true"></form:input>
							</div>
							<div class="col-sm-6 form-group">
								<form:label path="productName">Product Name</form:label>
								<form:input type="text" placeholder="Enter Product Name.."
									class="form-control" path="productName"></form:input>
							</div>
						</div>
						<div class="form-group">
							<form:label path="productDescription">Product Description</form:label>
							<form:textarea type="text" placeholder="Enter First Name.."
								class="form-control" path="productDescription"></form:textarea>
						</div>
						<div class="form-group">
							<form:label path="productType">Item Type</form:label>
							<form:radiobutton path="productType" value="Veg"/>Veg
							<form:radiobutton path="productType" value="NonVeg"/>Non Veg
						</div>
						<div class="form-group">
							<form:label path="productCuisine">Item Cuisine</form:label>
							<form:radiobutton path="productCuisine" value="Indian"/>Indian
							<form:radiobutton path="productCuisine" value="Chinese"/>Chinese
							<form:radiobutton path="productCuisine" value="Thai"/>Thai
						</div>
						<div class="form-group">
							<form:label path="productCategory">Item Course</form:label>
							<form:radiobutton path="productCategory" value="Starter"/>Starter
							<form:radiobutton path="productCategory" value="MainCourse"/>Main Course
							<form:radiobutton path="productCategory" value="Deserts"/>Deserts
							<form:radiobutton path="productCategory" value="Beverages"/>Beverages
						</div>
						<div class="row">
							<div class="col-sm-4 form-group">
								<form:label path="productPrice">Product Price</form:label>
								<form:input type="text" placeholder="Enter Item Price.."
									class="form-control" path="productPrice"></form:input>
							</div>
							<div class="col-sm-4 form-group">
								<form:label path="unitStock">Number of Products</form:label>
								<form:input type="text" placeholder="Number of Items.."
									class="form-control" path="unitStock"></form:input>
							</div>
						</div>
						<a class="form-actions">
							<button type="submit" class="btn btn-lg btn-info">Update</button>
						</a>
						<a href="<c:url value="/getAllProducts" />" class="btn btn-info"
						   style="margin-top: 0px; width: 150px; float: right; margin-right: 31px;">
							<span class="glyphicon glyphicon-arrow-left"></span>
						</a>
					</div>
				</form:form>
			</div>
		</div>
	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>