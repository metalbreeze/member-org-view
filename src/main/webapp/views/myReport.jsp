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
			<form:label path="money1">
				<spring:message text="服务费"/>
			</form:label>
		</td>
		<td>
			<form:input path="money1" readonly="true" />
		</td>
	</tr>
	<!-- 
	<tr>
		<td>
			<form:label path="money2">
				<spring:message text="服务费2"/>
			</form:label>
		</td>
		<td>
			<form:input path="money2" readonly="true" />
		</td>
	</tr> -->
	<tr>
		<td>
			<form:label path="">
				<spring:message text="总计"/>
			</form:label>
		</td>
		<td>
			<form:input path="" readonly="true" value="${ reportCenter.money2 + reportCenter.money1 }"/>
		</td>
	</tr>
	<tr>
		<td>
			<form:label path="">
				<spring:message text="余额"/>
			</form:label>
		</td>
		<td>
			<form:input path="" readonly="true" value="${ reportCenter.money2 + reportCenter.money1 - reportCenter.withdraw }"/>
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
		<td>
			<form:label path="withdrawRequest">
				<spring:message text="请求提现"/>
			</form:label>
		</td>
		<td>
			<form:input path="withdrawRequest"/>
		</td>	
		<td>
			<c:if test="${reportCenter.withdrawStatus == null || reportCenter.withdrawStatus == 0 || reportCenter.withdrawStatus == 2 || reportCenter.withdrawStatus == 4 }">
				<input type="button" onclick=myWithdraw()
							value="<spring:message text="提现"/>" />
			</c:if>
			${withdrawDescription[reportCenter.withdrawStatus]}
		</td>
	</tr>
</table>	
</form:form>

<h3>用户列表</h3>
<c:if test="${!empty listUsers}">
	<table class="tg" >
	<tr>
		<th width="20">序号</th>
		<th>注册时间</th>
		<th width="50">用户 ID</th>
		<th width="40">推荐人</th>
		<th width="40">姓名</th>
		<th width="60">手机</th>
		<th>微信号</th>
		<th>支付宝号</th>
		<th>银行帐号</th>
		<th>地址</th>
		<th width="20">身份</th>
		<th width="20">内/外网</th>
		<th width="30">激活</th>
		<th width="20">发货状态</th>
	</tr>
	<c:forEach items="${listUsers}" var="user" varStatus="loop">
		<tr>
			<td>${loop.index+1}</td>
			<td><fmt:formatDate pattern="yy-MM-dd HH:mm" value="${user.registerDate}" /></td>
			<td>${user.id}</td>
			<td>${user.parent.name}</td>
			<td>${user.name}</td>
			<td>${user.mobile}</td>
			<td><input type="text" value="${user.wechat}" size="5"/></td>
			<td><input type="text" value="${user.alipay}" size="5"/></td>
			<td><input type="text" value="${user.accountNumber}" size="5"/></td>
			<td>
				<input type="text" value="${user.address}" size="5"/></td>
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
						<c:choose>
							<c:when test="${reportCenter.electricMoney >= productList[user.product_id].price }">
								<a href="<c:url value='/myReport/active/${user.id}' />" >激活</a>
							</c:when>
							<c:otherwise>
								电子币不足
							</c:otherwise>
						</c:choose>
						<a href="<c:url value='/myReport/delete/${user.id}' />" 
						     onclick="return confirm('删除不可恢复,是否继续?');" >删除</a>
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
<script>
function myWithdraw()
{
	if(isNaN(document.forms[0].withdrawRequest.value) || document.forms[0].withdrawRequest.value <= 0)
	{
		alert("提现金额不对");
		document.forms[0].withdrawRequest.focus();
		return;
	}
	document.forms[0].submit();
}
</script>
</body>
</html>
