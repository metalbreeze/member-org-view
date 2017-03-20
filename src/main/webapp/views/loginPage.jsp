<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="Java" contentType="text/html;charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head><title>登录</title></head>
<body>
<jsp:include page="_menu.jsp" />
     
     <!-- /login?error=true -->
     <c:if test="${! empty param.error}">
         <div style="color:red;margin:10px 0px;">
          
                登录失败!!!<br />
                原因 :  ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
                 
         </div>
    </c:if>
     <div class="div_default">
   <form name='f' action="${pageContext.request.contextPath}/j_spring_security_check" method='POST'>
      <table>
         <tr>
            <td>用户名</td>
            <td><input type='text' name='username' value=''></td>
            <td><input name="submit" type="submit" onclick=register() value="注册" /></td>
         </tr>
         <tr>
            <td>密码</td>
            <td><input type='password' name='password' /></td>
            <td><input name="submit" type="submit" value="登录" /></td>
         </tr>
      </table>
  </form>
  </div>
</body>
<script type="text/javascript">
function register()
{
	document.forms[0].action="${pageContext.request.contextPath}/register";
	document.forms[0].submit();
}
</script>
</html>