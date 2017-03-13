<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="Java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
	<title>订单</title>
</head>
<body>
<jsp:include page="_menu.jsp" />

<h3>已发送订单列表-${product2.name}</h3>
<c:if test="${!empty list2}">
	<table class="tg">
	<tr>
		<th width="80">用户 ID</th>
		<th width="120">用户姓名</th>
		<th width="120">用户手机</th>
		<th width="120">地址</th>
	</tr>
	<c:forEach items="${list2}" var="user">
		<tr>
			<td>${user.id}</td>
			<td>${user.name}</td>
			<td>${user.mobile}</td>
			<td>${user.address}</td>
		</tr>
	</c:forEach>
	</table>
</c:if>
<h3>已发送订单列表-${product3.name}</h3>
<c:if test="${!empty list3}">
	<table class="tg">
	<tr>
		<th width="80">用户 ID</th>
		<th width="120">用户姓名</th>
		<th width="120">用户手机</th>
		<th width="120">地址</th>
	</tr>
	<c:forEach items="${list3}" var="user">
		<tr>
			<td>${user.id}</td>
			<td>${user.name}</td>
			<td>${user.mobile}</td>
			<td>${user.address}</td>
		</tr>
	</c:forEach>
	</table>
</c:if>
</body>
</html>
