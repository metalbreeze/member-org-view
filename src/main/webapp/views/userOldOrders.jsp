<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="Java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
	<title>订单</title>
</head>
<body>
<jsp:include page="_menu.jsp" />

<h3>已发送订单列表</h3>
<c:if test="${!empty list2}">
	<table class="tg">
	<tr>
		<th width="80">用户 ID</th>
		<th width="120">用户姓名</th>
		<th width="120">用户手机</th>
		<th width="120">地址</th>
		<th width="120">产品</th>
		<th width="120">发送日期</th>
	</tr>
	<c:forEach items="${list2}" var="user">
		<tr>
			<td>${user.id}</td>
			<td>${user.name}</td>
			<td>${user.mobile}</td>
			<td>${user.address}</td>
			<td>
				<c:choose>
					<c:when test="${user.product_id == null || user.product_id==0 }">
					没有选择产品
					</c:when>
					<c:otherwise>
						${productList[user.product_id].name}
					</c:otherwise>				
				</c:choose>
			</td>
			<td><fmt:formatDate pattern="yy-MM-dd HH:mm" value="${user.orderSendDate}" /></td>
		</tr>
	</c:forEach>
	</table>
</c:if>
</body>
</html>
