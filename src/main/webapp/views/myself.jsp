<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="false" %>
<%@ page language="Java" contentType="text/html;charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
	<title>用户Page</title>
</head>
<body>
<jsp:include page="_menu.jsp" />
<%-- 
<c:url var="addAction" value="/myself/edit" ></c:url>
--%>
<c:url var="withDrawAction" value="/myself/withDrawRequest" ></c:url>
<form:form action="${withDrawAction}" modelAttribute="user">
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
			<form:input path="mobile"  readonly="true" />
		</td>
	</tr>
	<tr>
		<td>
			<form:label path="wechat">
				<spring:message text="微信号"/>
			</form:label>
		</td>
		<td>
			<form:input path="wechat"  readonly="true" />
		</td>
	</tr>
	<tr>
		<td>
			<form:label path="alipay">
				<spring:message text="支付宝号"/>
			</form:label>
		</td>
		<td>
			<form:input path="alipay"  readonly="true" />
		</td>
	</tr>
	<tr>
		<td>
			<form:label path="accountNumber">
				<spring:message text="银行及帐号"/>
			</form:label>
		</td>
		<td>
			<form:input path="accountNumber"  readonly="true" />
		</td>
	</tr>
	<tr>
		<td>
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
			<form:input path="parent.id" readonly="true" hidden="true"/>
			${user.parent.name}
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
			${user.saleMoney +  user.bonusMoney + user.feedbackMoney }
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
				订单已发送
			</c:if>
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
		<c:if test="${user.withdrawStatus == 0}">
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
		<c:if test="${user.withdrawStatus == 1}">
			<td>
				<form:label path="withdrawRequest">
					<spring:message text="请求提现"/>
				</form:label>
			</td>
			<td>
				<form:input path="withdrawRequest" readonly="true" />(上次提现请求在等待批准,不能再次申请)
			</td>
		</c:if>
		<c:if test="${user.withdrawStatus == 2}">
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
<c:if test="${!empty levelUsers}">
<h3>所在群</h3>
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
<c:if test="${!empty list}">
<h3>推荐人员</h3>
<table class="tg">
	<tr>
		<th width="80">ID</th>
		<th width="60">姓名</th>
		<th width="60">激活时间</th>
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
			${user.activeDate}
		</td>
	</tr>
	</c:forEach>
</table>
</c:if>

</body>
</html>
