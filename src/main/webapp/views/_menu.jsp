<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ page language="Java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon" />
<style type="text/css">
	.tg  {border-collapse:collapse;border-spacing:0;border-color:#ccc;}
	.tg td {font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;
			border-style:solid;border-width:1px;overflow:hidden;word-break:normal;
			border-color:#ccc;color:#333;background-color:#c6f8ad;}
	.tg th {font-family:Arial, sans-serif;font-size:14px;font-weight:normal;
			padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;
			word-break:normal;border-color:#ccc;color:#333;background-color:#acf6af;}
	.tg .tg-4eph{background-color:#f9f9f9}
	.alertMsg {font-size:20px;color:red}
	body {background-color: #befdb8;}
	input {background-color: #befdb8;}
	body {font-family:Arial, sans-serif;font-size:14px;}
     #wrap{
         overflow: hidden;
         width: 815px;
     }
     #wrap .wrapIn{
         width: 8000%;
         height: 30px;
     }
     #wrap .wrapIn .div_float{
         float: left;
         margin-right: 50px;
     }
     .div_default {style="border: 1px solid #ccc; padding: 5px; margin-bottom: 20px;"}
</style>
<script
  src="https://code.jquery.com/jquery-3.1.1.min.js"
  integrity="sha256-hVVnYaiADRTO2PzUGmuLJr8BLUSjGIZsDYGmIJLv2b8="
  crossorigin="anonymous"></script>
<script
  src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"
  integrity="sha256-VazP97ZCwtekAsvgPBSUwPFKdrwD3unUfSGVYrahUqU="
  crossorigin="anonymous"></script>
<div style="border: 1px solid #ccc; padding: 5px; margin-bottom: 20px;background-color: #a4ee9f;" >
<span style="font-size:30px">绿康科技商务管理系统</span><br/>
<%-- testing https://jqueryui.com/menu/ --%>
	<a href="${pageContext.request.contextPath}/brief">关于我们</a> 
	<sec:authorize access="!hasRole('ROLE_USER')">
    	|&nbsp;<a href="${pageContext.request.contextPath}/login">登录</a>
    	|&nbsp;<a href="${pageContext.request.contextPath}/register">注册</a>
	</sec:authorize>
	<sec:authorize access="hasRole('ROLE_USER')">
		|&nbsp;<a href="${pageContext.request.contextPath}/myself">个人信息</a> 
	</sec:authorize>
	<sec:authorize access="hasAnyRole('ROLE_SENDER','ROLE_SENDER_VIEW')">
		|&nbsp;<a href="${pageContext.request.contextPath}/product/orders">快递单</a>
		|&nbsp;<a href="${pageContext.request.contextPath}/product/oldOrders">已发送快递单</a>
	</sec:authorize>
	<sec:authorize access="hasRole('ROLE_REPORT')">
		|&nbsp;<a href="${pageContext.request.contextPath}/myReport">我的报单</a> 
	</sec:authorize>
	<sec:authorize access="hasRole('ROLE_FINANCE')">
		|&nbsp;<a href="${pageContext.request.contextPath}/financeWithdraw">提现管理</a> 
		|&nbsp;<a href="${pageContext.request.contextPath}/financeWithdraw/already">已经提现</a>
	</sec:authorize>
	<sec:authorize access="hasRole('ROLE_USER')">
		|&nbsp;<a href="${pageContext.request.contextPath}/saleMoneyList">排行榜</a>
	</sec:authorize>
	<sec:authorize access="hasRole('ROLE_ADMIN')">
		|&nbsp;<a href="${pageContext.request.contextPath}/userFinance">会员账务</a>
		|&nbsp;<a href="${pageContext.request.contextPath}/platformFinance">平台账务</a>
		|&nbsp;<a href="${pageContext.request.contextPath}/listoperations">账务流水</a>
		|&nbsp;<a href="${pageContext.request.contextPath}/reportCenters">销售中心管理</a>
		|&nbsp;<a href="${pageContext.request.contextPath}/users">用户管理</a>
		|&nbsp;<a href="${pageContext.request.contextPath}/groups">群管理</a>
	</sec:authorize>
	<sec:authorize access="hasRole('ROLE_USER')">
    	|&nbsp;<a href="${pageContext.request.contextPath}/logout">退出</a>
    </sec:authorize>
</div>

<c:if test="${!empty flashMsg}">
<div style="border: 1px solid #eee; padding:5px; margin-bottom: 20px;">
 	<span class="alertMsg">
	${flashMsg}
	</span>
</div>
</c:if>
