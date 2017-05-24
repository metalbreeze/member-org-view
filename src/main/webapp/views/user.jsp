<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
			<form:label path="parent.name">
				<spring:message text="推荐人"/>
			</form:label>
		</td>
		<td>
			<form:input path="parent.name" />
			<%-- 
			 <form:select path="parent.id" items="${listUsers}" itemLabel="name" itemValue="id" value=""/>
			--%>
		</td>
	</tr>
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
			<form:label path="siteStatus">
				<spring:message text="网络"/>
			</form:label>
		</td>
		<td>
			<c:if test="${!empty user.name}">
			   <form:radiobutton path="siteStatus" value="1" />外网
			   <form:radiobutton path="siteStatus" value="2" />内网
			   <form:radiobutton path="siteStatus" value="3" /> 内/外网
			</c:if>
			<c:if test="${empty user.name}">
			   <form:radiobutton path="siteStatus" value="1" disabled="true"/>外网
			   <form:radiobutton path="siteStatus" value="2" checked="checked"/> 内网
			</c:if>
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
				<spring:message text="销售中心"/>
			</form:label>
		</td>
		<td>
			<form:select path="reportCenter.id" items="${listReportCenters}" itemLabel="name" itemValue="id" value=""/>
		</td>
	</tr>
	<tr>
		<td>
			<form:label path="portalBsiteMoney">
				<spring:message text="内网资金"/>
			</form:label>
		</td>
		<td>
			<form:input path="portalBsiteMoney" readonly="true"/>
		</td>
	</tr>
	<tr>
		<td>
			<form:label path="portalBsiteMoney2">
				<spring:message text="添加内网资金"/>
			</form:label>
		</td>
		<td>
			<form:input path="portalBsiteMoney2"/>
		</td>
	</tr>
	<tr>
		<td>
			<form:label path="portalBsiteCode">
				<spring:message text="内网编号"/>
			</form:label>
		</td>
		<td>
			<form:input path="portalBsiteCode"/>
		</td>
	</tr>
<!--
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
-->
	<tr>
		<td colspan="2">
			<c:if test="${!empty user.name}">
				<input type="button" onclick=checkAndSubmit()
					value="<spring:message text="修改"/>" />
			</c:if>
			<c:if test="${empty user.name}">
				<input type="button" onclick=checkAndSubmit()
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
		<th>序号</th>
		<th>注册时间</th>
		<th>用户 ID</th>
		<th>推荐人</th>
		<th>用户姓名</th>
		<th>用户手机</th>
		<th>修改</th>
		<th>内/外网</th>
		<th>内网编号</th>
		<%--<th width="60">删除</th>--%>
		<th>微信号</th>
		<th>支付宝号</th>
		<th>银行及帐号</th>
		<th>地址</th>
		<th>身份</th>

	</tr>
	<c:forEach items="${listUsers}" var="user" varStatus="loop">
		<tr>
			<td>${loop.index+1}</td>
			<td><fmt:formatDate pattern="yy-MM-dd HH:mm" value="${user.activeDate}" /></td>
			<td>${user.id}</td>
			<td>${user.parent.name}</td>
			<td>${user.name}</td>
			<td>${user.mobile}</td>
			<td><a href="<c:url value='/user/edit/${user.id}' />" >修改</a></td>
			<td>
			<c:choose>
				<c:when test="${user.siteStatus == 1 }">
					外网
				</c:when>
				<c:when test="${user.siteStatus == 2 }">
					内网
				</c:when>
				<c:when test="${user.siteStatus == 3 }">
					内/外网
				</c:when>
				<c:otherwise>
					状态不明
				</c:otherwise>
			</c:choose>
			</td>
			<td>${user.portalBsiteCode}</td>
		<%--	<td><a href="<c:url value='/user/remove/${user.id}' />" >删除</a></td>  --%>
			<td><input type="text" value="${user.wechat}" size="5"/></td>
			<td><input type="text" value="${user.alipay}" size="5"/></td>
			<td><input type="text" value="${user.accountNumber}" size="5"/></td>
			<td><input type="text" value="${user.address}" size="5"/></td>
			<td><c:choose>
					<c:when test="${user.available == -1 }">
						暂停会员
					</c:when>
					<c:when test="${user.status == 'old' }">
						会员
					</c:when>
					<c:when test="${user.status == null}">
						待激活会员
					</c:when>
					<c:when test="${user.status == 'new' }">
						待激活会员
					</c:when>
					<c:otherwise>
						状态不明
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
	</c:forEach>
	</table>
</c:if>
<script type="text/javascript" >
function checkAndSubmit()
{
	
	if($("#reportCenter\\.id").val()==0){
		alert("报单中心不能为空");	
		return;
	};
	if ( document.forms[0].name.value == "" )
	{
		alert("姓名不能为空");
		document.forms[0].name.focus();
		return;
	}
	document.forms[0].submit();
}
function siteSelect()
{
	if($("input[type='radio'][name='siteStatus']:checked").val()==1)
	{
		$("#product_id").empty();
		<c:forEach items="${listProducts}" var="p1">
			$("#product_id").append('<option value="${p1.id}">${p1.name}</option>');
		</c:forEach>
	}
	if($("input[type='radio'][name='siteStatus']:checked").val()==2)
	{
		$("#product_id").empty();
		<c:forEach items="${listSiteBProducts}" var="p1">
			$("#product_id").append('<option value="${p1.id}">${p1.name}</option>');
		</c:forEach>
	}
}
</script>
</body>
</html>
