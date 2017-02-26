<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="Java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
	<title>报单中心</title>
</head>
<body>
<jsp:include page="_menu.jsp" />
<h1>
	<c:if test="${!empty reportCenter.name}">
	修改报单中心
	</c:if>
	<c:if test="${empty reportCenter.name}">
	添加报单中心
	</c:if>
</h1>
<c:url var="addAction" value="/reportCenter/add" ></c:url>

<form:form action="${addAction}" commandName="reportCenter">
<table>
	<c:if test="${!empty reportCenter.id}">
	<tr>
		<td>
			<form:label path="id">
				<spring:message text="ID"/>
			</form:label>
		</td>
		<td>
			<form:input path="id" readonly="true" size="8"  disabled="true" />
			<form:hidden path="id" />
		</td> 
	</tr>
	</c:if>
	<tr>
		<td>
			<form:label path="name">
				<spring:message text="报单中心名字"/>
			</form:label>
		</td>
		<td>
			<form:input path="name" />
		</td> 
	</tr>
	<tr>
		<td>
			<form:label path="money">
				<spring:message text="报单中心余额"/>
			</form:label>
		</td>
		<td>
			<form:input path="money" />
		</td> 
	</tr>
	<tr>
		<td>
			<form:label path="owner.id">
				<spring:message text="推荐人"/>
			</form:label>
		</td>
		<td>
			<form:select path="owner.id" items="${listUsers}" itemLabel="name" itemValue="id" value=""/>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<c:if test="${!empty reportCenter.name}">
				<input type="submit"
					value="<spring:message text="修改"/>" />
			</c:if>
			<c:if test="${empty reportCenter.name}">
				<input type="submit"
					value="<spring:message text="添加"/>" />
			</c:if>
		</td>
	</tr>
</table>	
</form:form>
<br>
<h3>报单中心列表</h3>
<c:if test="${!empty listReportCenters}">
	<table class="tg">
	<tr>
		<th width="80">报单中心 ID</th>
		<th width="120">报单中心名字</th>
		<th width="120">余额</th>
		<th width="120">负责人</th>
		<th width="60">修改</th>
		<th width="60">删除</th>
		<th width="60">详细</th>
	</tr>
	<c:forEach items="${listReportCenters}" var="reportCenter">
		<tr>
			<td>${reportCenter.id}</td>
			<td>${reportCenter.name}</td>
			<td>${reportCenter.money}</td>
			<td>${reportCenter.owner.name}</td>
			<td><a href="<c:url value='/reportCenter/edit/${reportCenter.id}' />" >修改</a></td>
			<td><a href="<c:url value='/reportCenter/remove/${reportCenter.id}' />" >删除</a></td>
			<td><a href="<c:url value='/reportCenter/${reportCenter.id}' />" >详细</a></td>
		</tr>
	</c:forEach>
	</table>
</c:if>
</body>
</html>
