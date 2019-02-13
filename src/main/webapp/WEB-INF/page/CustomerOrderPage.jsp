<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>

<!-- 	navigation Bar -->
<%@ include file="navbar.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Product Management</title>
    <link rel="icon" type="image/x-icon"
          href="<c:url value="../resource/images/favicon1.png"/>" />
    <link rel="stylesheet"
          href="<c:url value="../resource/bootstrap/css/bootstrap.min.css"/>">
    <script src="<c:url value="../resource/js/jquery.js"/>"></script>
    <script src="<c:url value="../resource/bootstrap/js/bootstrap.min.js"/>"></script>
    <link rel="stylesheet" type="text/css"
          href="<c:url value="../resource/css/ProductList.css"/>">
    <script
            src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
    <script src="<c:url value="../resource/js/productController.js"/>"></script>
</head>
<body ng-app="myapp">
<div class="container" id="CustomerOrderTable"
     style="width: 1145px; margin-bottom: 180px;">
    <security:authorize access="hasRole('ROLE_ADMIN')">
        <h2>Admin Order Management</h2>
        <p>The List of Customer Order in our Database</p>
    </security:authorize>
    <security:authorize access="hasRole('ROLE_USER')">
        <h2>Ordered Items</h2>
    </security:authorize>
    <table class="table table-hover" id="CustomerOrderList">
        <thead>
        <tr>
            <th>Customer ID</th>
            <th>Item Name</th>
            <th>Item Quantity</th>
            <th>Item Status</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${customerOrd}" var="custord">
            <c:forEach items="${custord.orderItem}" var="orditem">
            <tr>
                <td>${custord.customer.customerId}</td>
                <td>${orditem.product.productName}</td>
                <td>${orditem.quality}</td>
                <td>NotProcessed</td>
            </tr>
            </c:forEach>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
<%@ include file="footer.jsp"%>