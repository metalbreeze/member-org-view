<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="Java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<html>
<head>
	<title>提现管理</title>
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
 <div class="div_default">
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
							提现等待批准
				</c:when>
				<c:when test="${user.withdrawStatus == 2 }">
							已经提现
				</c:when>
				<c:when test="${user.withdrawStatus == 3 }">
							<a href="<c:url value='/finance/withdraw/${user.id}' />" >
							提现
							</a>
				</c:when>
				<c:when test="${user.withdrawStatus == 4 }">
							不同意提现
				</c:when>		
			</c:choose>
		</td>
	</tr>
	</c:forEach>
</table>
</div>
<br/>
<div class="div_default">
<table class="tg">
	<tr>
		<th width="40">ID</th>
		<th width="60">销售中心</th>
		<th width="60">费用1</th>
		<th width="60">费用2</th>
		<th width="60">总计</th>
		<th width="60">已提现</th>
		<th width="60">余额</th>
		<th width="60">提现请求</th>
		<th width="60">提现状态</th>
	</tr>
	<c:forEach items="${listReportCenters}" var="reportCenter">
		<tr>
			<td>${reportCenter.id}</td>
			<td>${reportCenter.name}</td>
			<td align="right">${reportCenter.money1}</td>
			<td align="right">${reportCenter.money2}</td>
			<td align="right">${reportCenter.money2+reportCenter.money1}</td>
			<td align="right">${reportCenter.withdraw}</td>
			<td align="right">${reportCenter.money2+reportCenter.money1-reportCenter.withdraw}</td>
			<td align="right">
				${reportCenter.withdrawRequest}
			</td>
			<td align="right">
				<c:choose>
					<c:when test="${reportCenter.withdrawStatus == 1 }">
								等待管理员批准
					</c:when>
					<c:when test="${reportCenter.withdrawStatus == 3 }">
								<a href="<c:url value='/financeReportCenter/${reportCenter.id}' />" >提现</a>
					</c:when>
					<c:when test="${reportCenter.withdrawStatus == 4 }">
								不同意提现
					</c:when>
					<c:when test="${reportCenter.withdrawStatus == 2 }">
								已提现
					</c:when>
				</c:choose>
			</td>
		</tr>
	</c:forEach>
</table>	
</div>
	
</body>
</html>
