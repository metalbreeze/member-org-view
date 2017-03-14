<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="Java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
	<title>订单</title>
	<style type="text/css">
		.tg  {border-collapse:collapse;border-spacing:0;border-color:#ccc;}
		.tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#fff;}
		.tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#f0f0f0;}
		.tg .tg-4eph{background-color:#f9f9f9}
	</style>
</head>
<body>
<jsp:include page="_menu.jsp" />

<h3>订单列表-${product2.name}</h3>
<c:if test="${!empty list2}">
	<table class="tg">
	<tr>
		<th width="80">用户 ID</th>
		<th width="120">用户姓名</th>
		<th width="120">用户手机</th>
		<th width="120">地址</th>
		<th width="120">产品</th>
		<th width="120">送出快递单</th>
	</tr>
	<c:forEach items="${list2}" var="user">
		<tr>
			<td>${user.id}</td>
			<td>${user.name}</td>
			<td>${user.mobile}</td>
			<td>${user.address}</td>
			<td>
			<c:choose>
				<c:when test="${user.product_id == null || user.product_id==0 || user.product_id==1}">
					${productList[2].name}
				</c:when>
				<c:otherwise>
					${productList[user.product_id].name}
				</c:otherwise>				
			</c:choose>
			</td>
			<td><a href="<c:url value='/product/edit/${user.id}' />" >发送</a></td>
		</tr>
	</c:forEach>
	</table>
</c:if>
<h3>订单列表-${product3.name}</h3>
<c:if test="${!empty list3}">
	<table class="tg">
	<tr>
		<th width="80">用户 ID</th>
		<th width="120">用户姓名</th>
		<th width="120">用户手机</th>
		<th width="120">地址</th>
		<th width="120">产品</th>
		<th width="120">送出快递单</th>
	</tr>
	<c:forEach items="${listUsers}" var="user">
		<tr>
			<td>${user.id}</td>
			<td>${user.name}</td>
			<td>${user.mobile}</td>
			<td>${user.address}</td>
			<td>
			<c:choose>
				<c:when test="${user.product_id == null || user.product_id==0 || user.product_id==1}">
					${productList[2].name}
				</c:when>
				<c:otherwise>
					${productList[user.product_id].name}
				</c:otherwise>				
			</c:choose>
			</td>
			<td><a href="<c:url value='/product/edit/${user.id}' />" >发送</a></td>
		</tr>
	</c:forEach>
	</table>
</c:if>
</body>
</html>
