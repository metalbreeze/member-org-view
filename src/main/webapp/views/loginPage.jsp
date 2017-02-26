<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="Java" contentType="text/html;charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head><title>登录</title></head>
<body>
<div align="center" style="border: 1px solid #ccc; padding: 5px; margin-bottom: 20px;">
<span style="font-size:30px">商务管理系统</span>
</div> 
     
     <!-- /login?error=true -->
     <c:if test="${! empty param.error}">
         <div style="color:red;margin:10px 0px;">
          
                登录失败!!!<br />
                原因 :  ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
                 
         </div>
    </c:if>
     
   <form name='f' action="${pageContext.request.contextPath}/j_spring_security_check" method='POST'>
      <table>
         <tr>
            <td>用户名</td>
            <td><input type='text' name='username' value=''></td>
         </tr>
         <tr>
            <td>密码</td>
            <td><input type='password' name='password' /></td>
         </tr>
         <tr>
            <td><input name="submit" type="submit" value="登录" /></td>
            <td><a href="${pageContext.request.contextPath}/register">注册</a></td>
         </tr>
      </table>
  </form>
  管理员 admin20 密码12345
 报单用户  report1 密码12345
  普通用户 user1 密码12345
</body>
</html>