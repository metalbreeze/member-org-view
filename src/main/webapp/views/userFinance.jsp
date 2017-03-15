<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="Java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
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
		<th width="40">ID</th>
		<th width="60">姓名</th>
		<th width="60">销售奖励</th>
		<th width="60">分红</th>
		<th width="60">回馈</th>
<%-- 		<th width="60">个人业绩领导奖励</th>
		<th width="60">团队业绩领导奖励</th> --%>
		<th width="60">总计</th>
		<th width="60">应发奖金</th>
		<th width="60">已经提现</th>
		<th width="60">资金余额</th>
		<th width="60">提现请求</th>
		<th width="60">提现状态</th>
	</tr>
	<c:forEach items="${userList}" var="user">
	<tr>
		<td align="center">
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
			value="${ user.saleMoney + ( user.bonusMoney + user.feedbackMoney ) * 0.8 - user.withdraw}" />
		</td>
		<td align="right">
			${user.withdrawRequest}
		</td>
		<td align="right">
			<c:choose>
				<c:when test="${user.withdrawStatus == 1 }">
					<sec:authorize access="hasRole('ROLE_FINANCE')">
							提现等待批准
					</sec:authorize>
					<sec:authorize access="hasRole('ROLE_ADMIN')">
							<a href="<c:url value='/platformWithdraw/agree/${user.id}' />" >同意</a>
							<a href="<c:url value='/platformWithdraw/disagree/${user.id}' />" >不同意</a>
					</sec:authorize>
				</c:when>
				<c:when test="${user.withdrawStatus == 2 }">
							已经提现
				</c:when>
				<c:when test="${user.withdrawStatus == 3 }">
					<sec:authorize access="hasRole('ROLE_FINANCE')">
							<a href="<c:url value='/finance/withdraw/${user.id}' />" >
							给客户提现(请在微信上操作)
							</a>
					</sec:authorize>
					<sec:authorize access="hasRole('ROLE_ADMIN')">
							等待财务人员操作
					</sec:authorize>
				</c:when>
				<c:when test="${user.withdrawStatus == 4 }">
							不同意提现
				</c:when>		
			</c:choose>
		</td>
	</tr>
	</c:forEach>
</table>	
</body>
</html>
