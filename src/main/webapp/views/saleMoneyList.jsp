<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="Java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
	<title>平台帐务</title>
</head>
<body>
<jsp:include page="_menu.jsp" />
<h1>

</h1>


<table class="tg">
	<tr>
		<th width="80">ID</th>
		<th width="60">姓名</th>
		<th width="60">销售奖励</th>
		<th width="60">分红</th>
		<th width="60">回馈</th>
		<th width="60">总计</th>
	</tr>
	<c:forEach items="${userList}" var="user">
	<tr>
		<td>
			${user.id}
		</td>
		<td>
			${user.name }
		</td>
		<td align="right">
			${user.saleMoney}
		</td>
		<td align="right">
			${user.bonusMoney}
		</td>
		<td align="right">
			${user.feedbackMoney}
		</td>
		<td align="right">
			<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" 
			value="${user.saleMoney + user.bonusMoney + user.feedbackMoney }" />
		</td>
	</tr>
	</c:forEach>
</table>	
</body>
</html>
