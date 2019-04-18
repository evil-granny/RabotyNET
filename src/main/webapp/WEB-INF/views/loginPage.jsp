<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<body onload='document.loginForm.username.focus();'>
<h3>Login page</h3>

<c:if test="${not empty error}"><div style="color:red; font-weight: bold; margin: 30px 0px;">${error}</div></c:if>
<c:if test="${not empty message}"><div style="color:red; font-weight: bold; margin: 30px 0px;">${message}</div></c:if>

<div>
    <div class="panel-body">
<form name='login' action="<c:url value='/loginPage' />" method='POST'>
    <table>
        <tr>
            <td>UserName:</td>
            <td><input type='text' name='username' value=''></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type='password' name='password' /></td>
        </tr>
        <tr>
            <td colspan='2'><input name="submit" type="submit" value="submit" /></td>
        </tr>
    </table>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>
    </div>
</div>
</body>
</html>