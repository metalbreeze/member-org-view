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
	层级关系
</h1>
<c:if test="${!empty message}">
		<h2>	${message}</h2>
		组1：
		<a href="<c:url value="/listgroup?group.id="/>${group1.id}">${group1.name}</a>
		<br/>
		组2：
		<a href="<c:url value="/listgroup?group.id="/>${group2.id}">${group2.name}</a>
</c:if>
<c:url var="addAction" value="/group/addUser" ></c:url>
<form:form action="${addAction}" modelAttribute="user">


<table  style="border:3px #FFAC55 dashed;padding:5px;" rules="all" cellpadding='5';>
	<tr><td>
		<form:select path="group.id" items="${groupList}" itemLabel="name" itemValue="id" value="${id}"/>
		</td><td>
		<form:select path="level" items="${aviableLabels}" />
		</td><td>
		<form:select path="id" items="${users}" itemLabel="name" itemValue="id" />
		</td>
			<td colspan="2">
			<c:if test="${!empty user.level}">
				<input type="submit"
					value="<spring:message text="修改"/>" />
			</c:if>
			<c:if test="${empty user.level}">
				<input type="submit"
					value="<spring:message text="添加"/>" />
			</c:if>
			</td>
		</tr>
	<c:if test="${!empty group.name}">
	<tr>
		<td>
				组名
		</td> 
		<td>
				${group.name}
		</td> 
	</tr>
	
	
		<c:forEach items="${labels}" var="label">
		<tr>
			<td>${label}层</td>
			<c:forEach items="${levelUsers[label]}" var="list">
					<td>
					 ${list.name}
					</td>
    		</c:forEach>
   		</tr>
		</c:forEach>
	</c:if>
</table>

</form:form>
	
</body>
</html>
