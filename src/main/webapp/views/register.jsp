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
<h3>注册</h3>

<c:url var="addAction" value="/myself/edit" ></c:url>

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
			<form:label path="siteStatus">
				<spring:message text="网络"/>
			</form:label>
		</td>
		<td>
		   <form:radiobutton path="siteStatus" value="1" onclick="siteSelect()" checked="checked"/>外网
		   <form:radiobutton path="siteStatus" value="2" onclick="siteSelect()"/> 内网
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
			<form:select path="reportCenter.id">
				<form:option value="0" label="--- 请选择 ---"/>
				<form:options items="${listReportCenters}" itemLabel="name" itemValue="id" />
			</form:select>
		</td>
	</tr>
	<tr>
		<td colspan="2">
		<div style="text-align:center">
			<h3><spring:message text="注册协议"/></h3>
		</div>
			<textarea rows="35" cols="40">
一、总则
1.1 www.gxlvkangkeji.com所有权和运营权归广西绿康生物科技有限公司所有；
1.2 凡持有中华人民共和国居民身份证并年满十八周岁的(除受国家政策限制经商的人员除外)公民均可注册成为本公司的会员．
1.3 用户在注册之前，应当仔细阅读本协议，并同意遵守本协议后方可成为注册用户。一旦注册成功，则用户与公司之间自动形成协议关系，即默认用户自愿接受本协议的约束。用户在使用服务或产品时，应当同意接受相关协议后方能使用。 
1.4 本协议可由公司随时更新，用户应当及时关注并同意本公司不承担通知义务。本站的通知、公告、声明或其它类似内容是本协议的一部分。
二、使用规则
2.1 本公司不对外公开或向第三方提供单个用户的注册资料及用户在使用网络服务时存储在本站的非公开内容，但下列情况除外：
(1) 事先获得用户的明确授权； 
(2) 根据有关的法律法规要求；
2.2 本公司网站是销售服务系统，本系统由公司免费提供给会员使用，客户购买产品注册前均应详细了解该公司产品功能和销售模式，本产品是正规保健商品，客户购买产品后，不得退换，销售人员或广告代言人不得利用该网站进行夸大宣传，或者承诺式宣传，否则造成的一切后果由销售人员负责；
2.3 用户对本公司的服务有异议时，按本协议之规定处理；
2.4 本协议解释权和修订权属广西绿康科技有限公司所有．
			</textarea>
		</td>
	</tr>
	<tr>
		<td colspan="2">是否同意:
		 <input type="checkbox" name="isAgree" id="isAgree">(同意协议,请勾选完成注册)
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
	if ( document.forms[0].password.value != document.forms[0].password_2.value )
	{
			alert("2次密码不一样");
			document.forms[0].password.focus();
			return;
	}
	if ( document.forms[0].password.value == "" )
	{
			alert("没有设置密码");
			document.forms[0].password.focus();
			return;
	}
	if(!$("#isAgree").is(":checked")){
		alert("没用同意注册协议");
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
