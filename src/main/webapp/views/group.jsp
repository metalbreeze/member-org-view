<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="Java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
	<title>组</title>
</head>
<body>
<jsp:include page="_menu.jsp" />
<h1>
	<c:if test="${!empty group.name}">
	修改组
	</c:if>
	<c:if test="${empty group.name}">
	添加组
	</c:if>
</h1>
<c:url var="addAction" value="/group/add" ></c:url>

<form:form action="${addAction}" modelAttribute="group">
<table>
	<c:if test="${!empty group.id}">
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
				<spring:message text="姓名"/>
			</form:label>
		</td>
		<td>
			<form:input path="name" />
		</td> 
	</tr>
	<tr>
		<td>
			<form:label path="createDate">
				<spring:message text="创建时间"/>
			</form:label>
		</td>
		<td>
			<form:input readonly="true" path="createDate" />
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<c:if test="${!empty group.name}">
				<input type="submit"
					value="<spring:message text="修改"/>" />
			</c:if>
			<c:if test="${empty group.name}">
				<input type="submit"
					value="<spring:message text="添加"/>" />
			</c:if>
		</td>
	</tr>
</table>	
</form:form>
<br>
<h3>组列表</h3>
<c:if test="${!empty listGroups}">
	<table class="tg">
	<tr>
		<th width="80">组 ID</th>
		<th width="120">组姓名</th>
		<th width="120">创建时间</th>
		<th width="120">结束时间</th>
		<th width="60">修改</th>
		<th width="60">删除</th>
		<th width="60">查看群成员</th>
	</tr>
	<c:forEach items="${listGroups}" var="group">
		<tr>
			<td>${group.id}</td>
			<td>${group.name}</td>
			<td>${group.createDate}</td>
			<td>${group.endDate}</td>
			<td><a href="<c:url value='/group/edit/${group.id}' />" >修改</a></td>
			<td><a href="<c:url value='/group/remove/${group.id}' />" onclick="return confirm('删除不可恢复,是否继续?');" >删除</a></td>
			<td><a href="<c:url value='/listgroup?group.id=${group.id}'/>">查看群成员</a></td>
		</tr>
	</c:forEach>
	</table>
</c:if>
</body>
</html>
