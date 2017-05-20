<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="false" %>
<%@ page language="Java" contentType="text/html;charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
	<title>个人信息</title>
</head>
<body>
<jsp:include page="_menu.jsp" />

<%-- 
<c:url var="addAction" value="/myself/edit" ></c:url>
--%>
<c:url var="changePasswd" value="/myself/changePasswd" ></c:url>
<c:url var="withDrawAction" value="/myself/withDrawRequest" ></c:url>
<c:url var="BsiteWithDrawAction" value="/myself/BsiteWithDrawRequest" ></c:url>
<form:form action="" modelAttribute="user">
<table>
	<c:if test="${!empty user.name}">
	<tr>
		<td>
			<form:label path="id">
				<spring:message text="ID"/>
			</form:label>
		</td>
		<td>
			<form:input path="id" readonly="true" size="8"  align="center"/>
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
			<form:input path="name"  readonly="true" />
		</td> 
	</tr>
	<tr>
		<td>
			<form:label path="mobile">
				<spring:message text="手机"/>
			</form:label>
		</td>
		<td>
			<form:input path="mobile"  readonly="true"  align="center"/>
		</td>
	</tr>
	<tr>
		<td>
			<form:label path="wechat">
				<spring:message text="微信号"/>
			</form:label>
		</td>
		<td>
			<form:input path="wechat"  readonly="true" align="left"/>
		</td>
	</tr>
	<tr>
		<td>
			<form:label path="alipay">
				<spring:message text="支付宝号"/>
			</form:label>
		</td>
		<td>
			<form:input path="alipay"  readonly="true" align="left"/>
		</td>
	</tr>
	<tr>
		<td>
			<form:label path="accountNumber">
				<spring:message text="银行及帐号"/>
			</form:label>
		</td>
		<td>
			<form:input path="accountNumber"  readonly="true" align="left"/>
		</td>
	</tr>
	<tr>
		<td align="left">
			<form:label path="address">
				<spring:message text="地址"/>
			</form:label>
		</td>
		<td>
			<form:input path="address"  readonly="true" />
		</td>
	</tr>
	<tr>
		<td>
			<form:label path="parent.id">
				<spring:message text="推荐人"/>
			</form:label>
		</td>
		<td>
			<form:input path="" readonly="true" value="${user.parent.name}"/>
		</td>
	</tr>

	<tr>
		<td>
			<form:label path="saleMoney">
				<spring:message text="销售奖励"/>
			</form:label>
		</td>
		<td>
			<form:input path="saleMoney"  readonly="true" />
		</td>
	</tr>
	<tr>
		<td>
			<form:label path="bonusMoney">
				<spring:message text="分红"/>
			</form:label>
		</td>
		<td>
			<form:input path="bonusMoney"  readonly="true" />
		</td>
	</tr>
	<tr>
		<td>
			<form:label path="feedbackMoney">
				<spring:message text="回馈"/>
			</form:label>
		</td>
		<td>
			<form:input path="feedbackMoney"  readonly="true" />
		</td>
	</tr>
	<tr>
		<td>
			<form:label path="">
				<spring:message text="总计"/>
			</form:label>
		</td>
		<td>
			<form:input path="" readonly="true" value="${user.saleMoney +  user.bonusMoney + user.feedbackMoney }"/>
		</td>
	</tr>
	<tr>
		<td>
			<form:label path="">
				<spring:message text="应发奖金"/>
			</form:label>
		</td>
		<td>
		<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" 
			value="${ user.saleMoney + ( user.bonusMoney + user.feedbackMoney ) * 0.8 }" />
		</td>
	</tr>
	<tr>
		<td>
			<form:label path="">
				<spring:message text="已经提现"/>
			</form:label>
		</td>
		<td>
			<form:input path="withdraw"  readonly="true" />
		</td>
	</tr>
	<tr>
		<td>
			<form:label path="">
				<spring:message text="资金余额"/>
			</form:label>
		</td>
		<td>
			<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" 
			value="${ user.saleMoney + ( user.bonusMoney + user.feedbackMoney ) * 0.8 - user.withdraw}" />
		</td>
	</tr>
	<tr>
		<td>
			<form:label path="orderStatus">
				<spring:message text="订单状态"/>
			</form:label>
		</td>
		<td>
			<c:if test="${user.orderStatus == 0}">
				
			</c:if>
			<c:if test="${user.orderStatus == 1}">
				订单已提交
			</c:if>
			<c:if test="${user.orderStatus == 2}">
				已发货
			</c:if>
		</td>
	</tr>
	<tr>
		<td>
			<form:label path="withdrawRequest">
				<spring:message text="外网请求提现"/>
			</form:label>
		</td>
		<td>
			<form:input path="withdrawRequest"/>
		</td>	
		<td>
			<c:if test="${user.withdrawStatus == null || user.withdrawStatus == 0 || user.withdrawStatus == 2 || user.withdrawStatus == 4 }">
					<input type="button" onclick=myWithdraw()
							value="<spring:message text="提现"/>" />
			</c:if>
			${withdrawDescription[user.withdrawStatus]}
		</td>
	</tr>

	<tr>
		<td>
			<form:label path="">
				<spring:message text="内网金额"/>
			</form:label>
		</td>
		<td>
			<form:input path="portalBsiteMoney"  readonly="true" />
		</td>
	</tr>
	<tr>
		<td>
			<form:label path="">
				<spring:message text="内网已经提现"/>
			</form:label>
		</td>
		<td>
			<form:input path="portalBsiteWithdraw"  readonly="true" />
		</td>
	</tr>
		<tr>
		<td>
			<form:label path="">
				<spring:message text="内网余额"/>
			</form:label>
		</td>
		<td>
			<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" 
			value="${ user.portalBsiteMoney - user.portalBsiteWithdraw}" />
		</td>
	</tr>
	<tr>
		<td>
			<form:label path="portalBsiteWithdrawRequest">
				<spring:message text="内网请求提现"/>
			</form:label>
		</td>
		<td>
			<form:input path="portalBsiteWithdrawRequest"/>
		</td>	
		<td>
			<c:if test="${user.portalBsiteWithdrawRequestStatus == null || user.portalBsiteWithdrawRequestStatus == 0 || user.portalBsiteWithdrawRequestStatus == 2 || user.portalBsiteWithdrawRequestStatus == 4 }">
					<input type="button" onclick=myWithdrawBSite()
							value="<spring:message text="提现"/>" />
			</c:if>
			${withdrawDescription[user.portalBsiteWithdrawRequestStatus]}
		</td>
	</tr>
	
</table>	
</form:form>
<br />
<c:if test="${!empty currentGroup}">
<h3>工作群&nbsp;&nbsp;&nbsp;&nbsp;${currentGroup.name}</h3>
<table class="tg">
	<tr>
		<th>
				层级
		</th> 
		<th>
				群成员
		</th> 
	</tr>
	<c:forEach items="${labels}" var="label">
  	<tr>
		<td>&nbsp;</td>
		<c:forEach items="${currentGroup.levelUsers[label]}" var="list" varStatus="loop">
				<td style="font-family:Arial, sans-serif;font-size:11px;font-weight:normal;">
				 ${loop.index+1}
				</td>
   		</c:forEach>
  	</tr>
	<tr>
		<td>${label}层</td>
		<c:forEach items="${currentGroup.levelUsers[label]}" var="list" varStatus="loop">
				<td>
				 ${list.name}
				</td>
   		</c:forEach>
  	</tr>
	</c:forEach>
</table>
</c:if>
<br />
<c:if test="${!empty levelUsers}">
<h3>我的排位&nbsp;&nbsp;&nbsp;&nbsp;${group.name}</h3>
<table class="tg">
	<tr>
		<th>
				层级
		</th> 
		<th>
				群成员
		</th> 
	</tr>
	<c:forEach items="${labels}" var="label">
  	<tr>
		<td>&nbsp;</td>
		<c:forEach items="${levelUsers[label]}" var="list" varStatus="loop">
				<td style="font-family:Arial, sans-serif;font-size:11px;font-weight:normal;">
				 ${loop.index+1}
				</td>
   		</c:forEach>
  	</tr>
	<tr>
		<td>${label}层</td>
		<c:forEach items="${levelUsers[label]}" var="list">
				<td>
				 ${list.name}
				</td>
   		</c:forEach>
  	</tr>
	</c:forEach>
</table>
</c:if>
<br />
<c:if test="${!empty list}">
<h3>我的客户</h3>
<table class="tg">
	<tr>
		<th width="80">ID</th>
		<th width="60">姓名</th>
		<th width="60">注册时间</th>
	</tr>
	<c:forEach items="${list}" var="user">
	<tr>
		<td>
			${user.id}
		</td>
		<td>
			${user.name }
		</td>
		<td>
			<fmt:formatDate pattern="yy-MM-dd HH:mm" value="${user.activeDate}" />
		</td>
	</tr>
	</c:forEach>
</table>
</c:if>
<br /><br />
<form:form action="${changePasswd}" modelAttribute="user">
<table>
	<tr>
		<td colspan="2">
			<h3>修改密码</h3>
		</td> 
	</tr>

	<tr>
		<td>
			<form:label path="password">
				<spring:message text="旧密码"/>
			</form:label>
		</td>
		<td>
			<form:password path="password" />
		</td> 
	</tr>
	<tr>
		<td>
			<form:label path="password_2">
				<spring:message text="新密码"/>
			</form:label>
		</td>
		<td>
			<form:password path="password_2" />
		</td> 
	</tr>

	<tr>
		<td>
			<label for="password_3">
				重复新密码
			</label>
		</td>
		<td>
			<input id="password_3" name="password_3" type="password" value=""/>
		</td> 
	</tr>
	<tr>
		<td colspan="2">
				<input type="button" onclick=checkAndSubmit()
					value="<spring:message text="修改密码"/>" />
		</td>
	</tr>
</table>	
</form:form>
</body>
<script type="text/javascript" >
function checkAndSubmit()
{
	
	if ( document.forms[1].password_3.value != document.forms[1].password_2.value )
		{
			alert("密码不一样");
			document.forms[1].password_2.focus();   
		}
	else
		{
			document.forms[1].action="${changePasswd}";
			document.forms[1].submit();
		}
}
function myWithdraw()
{
	if(isNaN(document.forms[0].withdrawRequest.value) || document.forms[0].withdrawRequest.value <= 0)
	{
		alert("提现金额不对");
		document.forms[0].withdrawRequest.focus();
		return;
	}
	document.forms[0].action="${withDrawAction}";
	document.forms[0].submit();
}
function myWithdrawBSite()
{
	if(isNaN(document.forms[0].portalBsiteWithdrawRequest.value) || document.forms[0].portalBsiteWithdrawRequest.value <= 0)
	{
		alert("提现金额不对");
		document.forms[0].portalBsiteWithdrawRequest.focus();
		return;
	}
	document.forms[0].action="${BsiteWithDrawAction}";
	document.forms[0].submit();
}
</script>
</html>
