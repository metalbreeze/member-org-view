<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="Java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
	<title>用户Page</title>
	<style type="text/css">
		.tg  {border-collapse:collapse;border-spacing:0;border-color:#ccc;}
		.tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#fff;}
		.tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#f0f0f0;}
		.tg .tg-4eph{background-color:#f9f9f9}
	</style>
</head>
<body>
<jsp:include page="_menu.jsp" />
<h1>
	<c:if test="${!empty user.name}">
	修改用户
	</c:if>
	<c:if test="${empty user.name}">
	添加用户
	</c:if>
</h1>

<c:url var="addAction" value="/user/add" ></c:url>

<form:form action="${addAction}" modelAttribute="user">

<table>
	<c:if test="${!empty user.name}">
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
	</c:if>
	<tr>
		<td>
			<form:label path="name">
				<spring:message text="姓名"/>
			</form:label>
		</td>
		<td>
			<form:input path="name" />
		</td> 
	</tr>
	<tr>
		<td>
			<form:label path="mobile">
				<spring:message text="手机"/>
			</form:label>
		</td>
		<td>
			<form:input path="mobile" />
		</td>
	</tr>
	<tr>
		<td>
			<form:label path="wechat">
				<spring:message text="微信号"/>
			</form:label>
		</td>
		<td>
			<form:input path="wechat" />
		</td>
	</tr>
	<tr>
		<td>
			<form:label path="alipay">
				<spring:message text="支付宝号"/>
			</form:label>
		</td>
		<td>
			<form:input path="alipay" />
		</td>
	</tr>
	<tr>
		<td>
			<form:label path="accountNumber">
				<spring:message text="银行帐号"/>
			</form:label>
		</td>
		<td>
			<form:input path="accountNumber" />
		</td>
	</tr>
	<tr>
		<td>
			<form:label path="address">
				<spring:message text="地址"/>
			</form:label>
		</td>
		<td>
			<form:input path="address" />
		</td>
	</tr>
	<tr>
		<td>
			<form:label path="status">
				<spring:message text="身份"/>
			</form:label>
		</td>
		<td>
			<form:select path="status" items="${userStatus}" />
		</td>
	</tr>
	<tr>
		<td>
			<form:label path="parent.id">
				<spring:message text="推荐人"/>
			</form:label>
		</td>
		<td>
			<form:select path="parent.id" items="${listUsers}" itemLabel="name" itemValue="id" value=""/>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<c:if test="${!empty user.name}">
				<input type="submit"
					value="<spring:message text="修改"/>" />
			</c:if>
			<c:if test="${empty user.name}">
				<input type="submit"
					value="<spring:message text="添加"/>" />
			</c:if>
		</td>
	</tr>
</table>	
</form:form>
<br>
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
		<th width="60">修改</th>
		<th width="60">删除</th>
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
				</c:choose>
			</td>
			<td>${user.registerDate}</td>
			<td>${user.parent.name}</td>
			<td><a href="<c:url value='/user/edit/${user.id}' />" >修改</a></td>
			<td><a href="<c:url value='/user/remove/${user.id}' />" onclick="return confirm('删除不可恢复,是否继续?');" >删除</a></td>
		</tr>
	</c:forEach>
	</table>
</c:if>
</body>
</html>
