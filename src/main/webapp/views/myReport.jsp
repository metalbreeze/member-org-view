<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="Java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
	<title>报单</title>
</head>
<body>
<jsp:include page="_menu.jsp" />

<h1>
	${reportCenter.name }
</h1>
<c:url var="withDrawAction" value="/reportCenter/withDrawRequest" ></c:url>
<form:form action="${withDrawAction}" modelAttribute="reportCenter">
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
				<spring:message text="销售中心名字"/>
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
		<tr>
		<td>
			<form:label path="withdraw">
				<spring:message text="已经提现"/>
			</form:label>
		</td>
		<td>
			<form:input path="withdraw"  readonly="true" />
		</td>
	</tr>
	<tr>
		<c:if test="${reportCenter.withdrawStatus == 0}">
			<td>
				<form:label path="withdrawRequest">
					<spring:message text="请求提现"/>
				</form:label>
			</td>
			<td>
				<form:input path="withdrawRequest"/>
			</td>
			<td>
				<input type="submit" value="<spring:message text="提现"/>" />
			</td>
		</c:if>
		<c:if test="${reportCenter.withdrawStatus == 1}">
			<td>
				<form:label path="withdrawRequest">
					<spring:message text="请求提现"/>
				</form:label>
			</td>
			<td>
				<form:input path="withdrawRequest" readonly="true" />(上次提现请求在等待批准,不能再次申请)
			</td>
		</c:if>
		<c:if test="${reportCenter.withdrawStatus == 2}">
			<td>
				<form:label path="withdrawRequest">
					<spring:message text="请求提现"/>
				</form:label>
			</td>
			<td>
				<form:input path="withdrawRequest"/>(上次提现已经批准)
			</td>
			<td>
				<input type="submit" value="<spring:message text="提现"/>" />
			</td>
		</c:if>
	</tr>
</table>	
</form:form>

<h3>用户列表</h3>
<c:if test="${!empty listUsers}">
	<table class="tg">
	<tr>
		<th width="20">序号</th>
		<th width="160">注册时间</th>
		<th width="50">用户 ID</th>
		<th width="70">推荐人</th>
		<th width="70">用户姓名</th>
		<th width="100">用户手机</th>
		<th width="100">微信号</th>
		<th width="100">支付宝号</th>
		<th width="120">银行帐号</th>
		<th width="120">地址</th>
		<th width="30">身份</th>
		<th width="30">激活</th>
		<th width="40">发货状态</th>
	</tr>
	<c:forEach items="${listUsers}" var="user" varStatus="loop">
		<tr>
			<td>${loop.index+1}</td>
			<td><fmt:formatDate pattern="yyyy-MM-dd KK:HH:mm" value="${user.registerDate}" /></td>
			<td>${user.id}</td>
			<td>${user.parent.name}</td>
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
			
			<td>
			    <c:choose>
					<c:when test="${user.status == 'new' || user.status == null }">
						<a href="<c:url value='/myReport/active/${user.id}' />" >激活</a>
						<a href="<c:url value='/myReport/delete/${user.id}' />" >删除</a>
					</c:when>
					<c:when test="${user.status == 'old' }">
						
					</c:when>
				</c:choose>
			</td>
			<td>
			    <c:choose>
					<c:when test="${user.orderStatus == 1 }">
						已出单
					</c:when>
					<c:when test="${user.orderStatus == 2 }">
						已发货
					</c:when>
				</c:choose>
			</td>
		</tr>
	</c:forEach>
	</table>
</c:if>
</body>
</html>
