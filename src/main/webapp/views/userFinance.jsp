<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
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
		<th width="60">直推额度</th>
		<th width="60">团队额度</th>
		<th width="60">分红</th>
		<th width="60">提现</th>
		<th width="60">资金余额</th>
	</tr>
	<c:forEach items="${userList}" var="user">
	<tr>
		<td>
			${user.id}
		</td>
		<td>
			${user.name }
		</td>
		<td>
			${user.directScore}
		</td>
		<td>
			${user.groupScore}
		</td>
		<td>
			${user.money}
		</td>
		<td>
			${user.withdraw}
		</td>
		<td>
			余额
		</td>
	</tr>
	</c:forEach>
</table>	
</body>
</html>
