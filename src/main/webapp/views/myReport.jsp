<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="Java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
	<title>我报单</title>
</head>
<body>
<jsp:include page="_menu.jsp" />

<h1>
	${reportCenter.name }
</h1>

<form:form action="" commandName="reportCenter">
<table>
	<tr>
		<td>
			<form:label path="id">
				<spring:message text="ID"/>
			</form:label>
		</td>
		<td>
			<form:input path="id" readonly="true" size="8" />
		</td> 
	</tr>
	<tr>
		<td>
			<form:label path="name">
				<spring:message text="报单中心名字"/>
			</form:label>
		</td>
		<td>
			<form:input path="name" readonly="true" />
		</td> 
	</tr>
	<tr>
		<td>
			<form:label path="money1">
				<spring:message text="服务费1"/>
			</form:label>
		</td>
		<td>
			<form:input path="money1" readonly="true" />
		</td>
	</tr>
	<tr>
		<td>
			<form:label path="money2">
				<spring:message text="服务费2"/>
			</form:label>
		</td>
		<td>
			<form:input path="money2" readonly="true" />
		</td>
	</tr>
	<tr>
		<td>
			<form:label path="electricMoney">
				<spring:message text="电子币"/>
			</form:label>
		</td>
		<td>
			<form:input path="electricMoney" readonly="true" />
		</td>
	</tr>
</table>	
</form:form>

<h3>用户列表</h3>
<c:if test="${!empty listUsers}">
	<table class="tg">
	<tr>
		<th width="80">用户 ID</th>
		<th width="120">用户姓名</th>
		<th width="120">用户手机</th>
		<th width="120">微信号</th>
		<th width="120">支付宝号</th>
		<th width="120">银行帐号</th>
		<th width="120">地址</th>
		<th width="120">身份</th>
		<th width="120">注册时间</th>
		<th width="120">推荐人</th>
		<th width="120">激活</th>
	</tr>
	<c:forEach items="${listUsers}" var="user">
		<tr>
			<td>${user.id}</td>
			<td>${user.name}</td>
			<td>${user.mobile}</td>
			<td>${user.wechat}</td>
			<td>${user.alipay}</td>
			<td>${user.accountNumber}</td>
			<td>${user.address}</td>
			<td>
				<c:choose>
					<c:when test="${user.status == 'new' }">
						新会员
					</c:when>
					<c:when test="${user.status == 'old' }">
						会员
					</c:when>
					<c:when test="${user.status == null }">
						新会员
					</c:when>
				</c:choose>
			</td>
			<td>${user.registerDate}</td>
			<td>${user.parent.name}</td>
			<td>
			    <c:choose>
					<c:when test="${user.status == 'new' }">
						<a href="<c:url value='/myReport/active/${user.id}' />" >激活</a>
					</c:when>
					<c:when test="${user.status == 'old' }">
						
					</c:when>
					<c:when test="${user.status == null }">
						<a href="<c:url value='/myReport/active/${user.id}' />" >激活</a>
					</c:when>
				</c:choose>
			</td>
		</tr>
	</c:forEach>
	</table>
</c:if>
</body>
</html>
