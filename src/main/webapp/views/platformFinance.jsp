<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="Java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
	<title>平台帐务</title>
</head>
<body>
<jsp:include page="_menu.jsp" />
<h1>
</h1>
<c:url var="changePlatfomCost" value="/platformCost" ></c:url>
<form:form action="${changePlatfomCost}">
<table>
	<tr>
		<td>其它支出1</td>
		<td><input type=text name="platformCost1"/></td>
	</tr>
	<tr>
		<td>其它支出2</td>
		<td><input type=text name="platformCost2"/></td>
	</tr>
	<tr>
		<td colspan="2"><input type="submit" value="修改"></td>
	</tr>
</table>
</form:form>
<table class="tg">
	<tr>
		<th width="80">销售总额</th>
		<th width="60">产品成本</th>
		<th width="60">实发奖金</th>
		<th width="60">销售中心服务费</th>
		<th width="60">其它支出1</th>
		<th width="60">其它支出2</th>
		<th width="60">合计支出</th>
		<th width="60">资金余额</th>
	</tr>
	<tr>
		<td align="right">
			${sellTotal}
		</td>
		<td align="right">
			${userCostMoney }
		</td>
		<td align="right">
			${totolAward}
		</td>
		<td align="right">
			${reportCenterCost}
		</td>
		<td align="right">
			${platformCost1}
		</td>
		<td align="right">
			${platformCost2}
		</td>
		<td align="right">
			${totalCost}
		</td>
		<td align="right">
			${remain}
		</td>
	</tr>
</table>	
</body>
</html>
