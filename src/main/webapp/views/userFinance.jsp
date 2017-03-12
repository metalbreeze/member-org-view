<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="Java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
	<title>会员账务</title>
</head>
<body>
<jsp:include page="_menu.jsp" />
<h1>

</h1>
<!-- 
<c:url var="addAction" value="/userFinanceByID" ></c:url>
<table class="tg">
	<tr>
		<td>
			<a href=<c:url value="/userFinanceByID?id=1"/>>选择重新计算</a>
		</td>
	</tr>
</table>
 -->
<table class="tg">
	<tr>
		<th width="80">ID</th>
		<th width="60">姓名</th>
		<th width="60">销售奖励</th>
		<th width="60">分红</th>
		<th width="60">回馈</th>
<%-- 		<th width="60">个人业绩领导奖励</th>
		<th width="60">团队业绩领导奖励</th> --%>
		<th width="60">总计</th>
		<th width="60">应发奖金</th>
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
			${user.saleMoney}
		</td>
		<td>
			${user.bonusMoney}
		</td>
		<td>
			${user.feedbackMoney}
		</td>
<%--	<td>
 		${user.personalScore}
		</td>
		<td>
			${user.groupScore}
		</td> --%>
		<td align="right">
			<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" 
			value="${user.saleMoney +  user.bonusMoney + user.feedbackMoney }" />
		</td>
		<td align="right">
			<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" 
			value="${ user.saleMoney + ( user.bonusMoney + user.feedbackMoney ) * 0.8 }" />
		</td>
		<td align="right">
			${user.withdraw}
		</td>
		<td align="right">
			<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" 
			value="${user.saleMoney + ( user.bonusMoney + user.feedbackMoney ) * 0.8  - user.withdraw}" />
		</td>
	</tr>
	</c:forEach>
</table>	
</body>
</html>
