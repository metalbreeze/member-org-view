<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ page language="Java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<style type="text/css">
	.tg  {border-collapse:collapse;border-spacing:0;border-color:#ccc;}
	.tg td {font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#fff;}
	.tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#f0f0f0;}
	.tg .tg-4eph{background-color:#f9f9f9}
	.alertMsg {font-size:20px;color:red}
</style>
<div style="border: 1px solid #ccc; padding: 5px; margin-bottom: 20px;">
<span style="font-size:30px">商务管理系统</span>
<br/>
	<a href="${pageContext.request.contextPath}/views/brief.jsp">简介</a> 
	<c:if test="${pageContext.request.userPrincipal.name == null}">
    	|&nbsp;<a href="${pageContext.request.contextPath}/login">登录</a>
    	|&nbsp;<a href="${pageContext.request.contextPath}/register">注册</a>
	</c:if>
	<sec:authorize access="hasRole('ROLE_USER')">
		|&nbsp;<a href="${pageContext.request.contextPath}/myself">用户信息</a> 
	</sec:authorize>
	<sec:authorize access="hasRole('ROLE_REPORT')">
		|&nbsp;<a href="${pageContext.request.contextPath}/myReport">我的报单</a> 
	</sec:authorize>
	<c:if test="${pageContext.request.userPrincipal.name == null}">
		|&nbsp;<a href="${pageContext.request.contextPath}/saleMoneyList">排行榜</a>
	</c:if>
	<sec:authorize access="hasRole('ROLE_ADMIN')">
		|&nbsp;<a href="${pageContext.request.contextPath}/userFinance">会员账务</a>
		|&nbsp;<a href="${pageContext.request.contextPath}/platformFinance">平台账务</a>
		|&nbsp;<a href="${pageContext.request.contextPath}/listoperations">账务流水</a>
		|&nbsp;<a href="${pageContext.request.contextPath}/reportCenters">报单中心管理</a>
		|&nbsp;<a href="${pageContext.request.contextPath}/users">用户管理</a>
		|&nbsp;<a href="${pageContext.request.contextPath}/groups">群管理</a>
	</sec:authorize>
	<c:if test="${pageContext.request.userPrincipal.name != null}">
    	|&nbsp;<a href="${pageContext.request.contextPath}/logout">退出</a>
	</c:if>

</div>

<c:if test="${!empty flashMsg}">
<div style="border: 1px solid #eee; padding:5px; margin-bottom: 20px;">
 	<span class="alertMsg">
	${flashMsg}
	</span>
</div>
</c:if>
