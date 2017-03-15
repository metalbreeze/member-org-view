<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="Java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
	<title>操作流水列表</title>
</head>
<body>
<jsp:include page="_menu.jsp" />
<h3>操作流水列表</h3>
<c:if test="${!empty operationList}">
	<table class="tg">
	<tr>
		<th width="80">ID</th>
		<th width="120">操作</th>
		<th width="120">用户</th>
		<th width="60">销售中心</th>
		<th width="60">金额</th>
		<th width="60">日期</th>
		<th width="60">备注</th>
	</tr>
	<c:forEach items="${operationList}" var="p">
		<tr>
			<td align="center">${p.id}</td>
			<td>${p.operation}</td>
			<td>${p.user.name}</td>
			<td>${p.reportCenter.name}</td>
			<td align="right">${p.money}</td>
			<td>${p.operationDate}</td>
			<td>${p.remark}</td>
		</tr>
	</c:forEach>
	</table>
</c:if>
</body>
</html>
