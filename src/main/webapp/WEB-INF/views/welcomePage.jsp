<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>RabotyNET - Welcome page</title>
    <link href="<c:url value='/static/vendor/bootstrap/css/bootstrap.min.css' />" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark static-top">
    <div class="container">
        <div class="collapse navbar-collapse" id="navbarResponsive">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="/homePage">Home</a>
                </li>
                <li class="nav-item active">
                    <a class="nav-link" href="/adminPage">Admin</a>
                </li>
                <li class="nav-item active">
                    <a class="nav-link" href="/cownerPage">Company owner</a>
                </li>
                <li class="nav-item active">
                    <a class="nav-link" href="/userPage">User</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<div class="container">
    <div class="row">
        <div class="col-lg-12 text-center">
            <h1 class="mt-5">Welcome to RabotyNET</h1>
            <p class="lead"><a href="${pageContext.request.contextPath}/loginPage">Login to RabotyNET</a></p>
        </div>
    </div>
</div>
<script src="<c:url value='/static/vendor/jquery/jquery.min.js' />"></script>
<script src="<c:url value='/static/vendor/bootstrap/js/bootstrap.bundle.min.js' />"></script>
</body>
</html>