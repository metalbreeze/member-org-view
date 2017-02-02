<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<%@ page language="Java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
	<title>组</title>
	<style type="text/css">
		.tg  {border-collapse:collapse;border-spacing:0;border-color:#ccc;}
		.tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#fff;}
		.tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#f0f0f0;}
		.tg .tg-4eph{background-color:#f9f9f9}
	</style>
</head>
<body>
<h1>
	添加组
</h1>

<c:url var="addAction" value="/group/add" ></c:url>

<form:form action="${addAction}" commandName="group">
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
		<th width="120">组手机</th>
		<th width="60">修改</th>
		<th width="60">删除</th>
	</tr>
	<c:forEach items="${listGroups}" var="group">
		<tr>
			<td>${group.id}</td>
			<td>${group.name}</td>
			<td>${group.createDate}</td>
			<td><a href="<c:url value='/group/edit/${group.id}' />" >修改</a></td>
			<td><a href="<c:url value='/group/remove/${group.id}' />" >删除</a></td>
		</tr>
	</c:forEach>
	</table>
</c:if>
</body>
</html>
