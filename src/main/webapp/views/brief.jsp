<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="Java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
	<title>简介</title>
</head>
<body>
<jsp:include page="_menu.jsp" />


<h1>
简介
</h1>
<c:url var="addAction" value="/group/add" ></c:url>

<form:form action="${addAction}" commandName="group">
<table>
</table>	
</form:form>
<br>
<h3>简介</h3>
	</body>
</html>