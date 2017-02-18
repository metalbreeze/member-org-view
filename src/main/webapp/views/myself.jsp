<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<%@ page language="Java" contentType="text/html;charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
	<title>用户Page</title>
</head>
<body>
<jsp:include page="_menu.jsp" />

<c:url var="addAction" value="/myself/edit" ></c:url>

<form:form action="${addAction}" commandName="user">
<table>
	<c:if test="${!empty user.name}">
	<tr>
		<td>
			<form:label path="id">
				<spring:message text="ID"/>
			</form:label>
		</td>
		<td>
			<form:input path="id" readonly="true" size="8"/>
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
			<form:label path="parent.id">
				<spring:message text="推荐人"/>
			</form:label>
		</td>
		<td>
			<form:input path="parent.id" readonly="true" hidden="true"/>
			${user.parent.name}
		</td>
	</tr>
	<tr>
		<td>
			<form:label path="reportCenter">
				<spring:message text="报单中心"/>
			</form:label>
		</td>
		<td>
			<form:input path="reportCenter.name" />
		</td>
	</tr>
	<tr>
		<td colspan="2">
		 <!-- 
			<c:if test="${!empty user.name}">
				<input type="submit"
					value="<spring:message text="修改"/>" />
			</c:if>
		-->
		</td>
	</tr>
</table>	
</form:form>
</body>
</html>
