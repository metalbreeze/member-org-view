<%@ page session="false" %>
<%@ page language="Java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
<title>拒绝访问</title>
</head>
<body>
<jsp:include page="_menu.jsp"/>
 
    <h3 style="color:red;">${message}</h3>
</body>
</html>