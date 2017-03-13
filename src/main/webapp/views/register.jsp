<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="Java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
	<title>注册</title>
</head>
<body>
<jsp:include page="_menu.jsp" />
<h1>注册</h1>

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
				<spring:message text="收货地址"/>
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
			<form:input path="parent.name" />
		</td>
	</tr>
	<tr>
		<td>
			<form:label path="product_id">
				<spring:message text="产品"/>
			</form:label>
		</td>
		<td>
			<form:select path="product_id" items="${listProducts}" itemLabel="name" itemValue="id" value=""/>
		</td>
	</tr>
	<tr>
		<td>
			<form:label path="reportCenter.id">
				<spring:message text="报单中心"/>
			</form:label>
		</td>
		<td>
			<form:select path="reportCenter.id" items="${listReportCenters}" itemLabel="name" itemValue="id" value=""/>
		</td>
	</tr>
	<tr>
		<td>
			<form:label path="password">
				<spring:message text="密码"/>
			</form:label>
		</td>
		<td>
			<form:password path="password" />
		</td> 
	</tr>
	<tr>
		<td>
			<form:label path="password_2">
				<spring:message text="重复密码"/>
			</form:label>
		</td>
		<td>
			<form:password path="password_2" />
		</td> 
	</tr>
	<tr>
		<td colspan="2">
				<input type="button" onclick=checkAndSubmit()
					value="<spring:message text="注册"/>" />
		</td>
	</tr>
</table>	
</form:form>
<script type="text/javascript" >
function checkAndSubmit()
{
	if ( document.forms[0].name == "" )
	{
		alert("姓名不能为空");
		document.forms[0].name.focus();
		return;
	}
	if ( document.forms[0].password.value != document.forms[0].password_2.value )
		{
			alert("密码不一样"+document.forms[0].password.value+document.forms[0].password_2.value);
			document.forms[0].password.focus();   
		}
	else
		{
			document.forms[0].submit();
		}
}
</script>
</body>
</html>
