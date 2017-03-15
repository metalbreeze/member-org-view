<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="Java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
	<title>销售中心</title>
</head>
<body>
<jsp:include page="_menu.jsp" />
<h1>
	<c:if test="${!empty reportCenter.name}">
	修改销售中心
	</c:if>
	<c:if test="${empty reportCenter.name}">
	添加销售中心
	</c:if>
</h1>
<c:url var="addAction" value="/reportCenter/add" ></c:url>

<form:form action="${addAction}" modelAttribute="reportCenter">
<table>
	<c:if test="${!empty reportCenter.id}">
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
				<spring:message text="销售中心名字"/>
			</form:label>
		</td>
		<td>
			<form:input path="name" />
		</td> 
	</tr>
	<tr>
		<td>
			<form:label path="money1">
				<spring:message text="费用1"/>
			</form:label>
		</td>
		<td>
			<form:input path="money1" />
		</td> 
	</tr>
	<tr>
		<td>
			<form:label path="money2">
				<spring:message text="费用2"/>
			</form:label>
		</td>
		<td>
			<form:input path="money2" />
		</td> 
	</tr>
		<tr>
		<td>
			<form:label path="electricMoney">
				<spring:message text="报单电子币"/>
			</form:label>
		</td>
		<td>
			<form:input path="electricMoney" />
		</td> 
	</tr>
	<tr>
		<td>
			<form:label path="owner.id">
				<spring:message text="推荐人"/>
			</form:label>
		</td>
		<td>
			<form:select path="owner.id" items="${listUsers}" itemLabel="name" itemValue="id" value=""/>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<c:if test="${!empty reportCenter.name}">
				<input type="submit"
					value="<spring:message text="修改"/>" />
			</c:if>
			<c:if test="${empty reportCenter.name}">
				<input type="submit"
					value="<spring:message text="添加"/>" />
			</c:if>
		</td>
	</tr>
</table>	
</form:form>
<br>
<h3>销售中心列表</h3>
<c:if test="${!empty listReportCenters}">
	<table class="tg">
	<tr>
		<th width="80">销售中心 ID</th>
		<th width="120">销售中心名字</th>
		<th width="120">费用1</th>
		<th width="120">费用2</th>
		<th width="120">总计</th>
		<th width="120">提现</th>
		<th width="120">余额</th>
		<th width="120">电子币</th>
		<th width="120">负责人</th>
		<th width="60">修改</th>
		<th width="60">删除</th>
		<th width="60">详细</th>
		<th width="60">提现请求</th>
		<th width="60">同意提现</th>
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
			<td align="right">${reportCenter.electricMoney}</td>
			<td align="right">${reportCenter.owner.name}</td>
			<td><a href="<c:url value='/reportCenter/edit/${reportCenter.id}' />" >修改</a></td>
			<td><a href="<c:url value='/reportCenter/remove/${reportCenter.id}' />" >删除</a></td>
			<td><a href="<c:url value='/reportCenter/${reportCenter.id}' />" >详细</a></td>
			<td align="right">
				${reportCenter.withdrawRequest}
			</td>
			<td align="right">
				<c:choose>
					<c:when test="${reportCenter.withdrawStatus == 1 }">
								<a href="<c:url value='/platformReportCenterWithDrawRequest/${reportCenter.id}' />" >同意提现</a>
					</c:when>
					<c:when test="${reportCenter.withdrawStatus == 2 }">
								已提现
					</c:when>
				</c:choose>
			</td>
		</tr>
	</c:forEach>
	</table>
</c:if>
</body>
</html>
