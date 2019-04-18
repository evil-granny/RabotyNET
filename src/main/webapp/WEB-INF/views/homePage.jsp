<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<a href="${pageContext.request.contextPath}/userPage">JD User</a> | <a href="${pageContext.request.contextPath}/cownerPage">JD COwner</a> | <a href="${pageContext.request.contextPath}/adminPage">JD Admin</a> | <a href="javascript:document.getElementById('logout').submit()">Logout</a>

<h3>Welcome to RabotyNET Home Page</h3>
<ul>
    <li><a href="${pageContext.request.contextPath}/persons">All persons page</a></li>
    <li><a href="${pageContext.request.contextPath}/person/1">Person id = 1</a></li>
    <li><a href="${pageContext.request.contextPath}/person/2">Person id = 2</a></li>
</ul>

<c:url value="/logout" var="logoutUrl" />
<form id="logout" action="${logoutUrl}" method="post" >
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>
