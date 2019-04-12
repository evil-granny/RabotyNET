<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Denys
  Date: 08.04.2019
  Time: 23:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Devcolibri.com exam REST</title>
</head>

<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script type="text/javascript">
    var prefix = '/temp';

    var RestGet = function() {
        $.ajax({
            type: 'GET',
            url:  prefix + '/' +'job',
            dataType: 'json',
            async: true,
            success: function(result) {
                alert('Время: ' + result);
            },
            error: function(jqXHR, textStatus, errorThrown) {
                alert(jqXHR.status + ' ' + jqXHR.responseText);
            }
        });
    };
</script>

<body>

<h3>Это простой пример использования REST c помощью Ajax</h3>

<button type="button" onclick="RestGet()">Метод GET</button>
<%--
<button type="button" onclick="RestPost()">Метод POST</button>
<button type="button" onclick="RestDelete()">Метод DELETE</button>
<button type="button" onclick="RestPut()">Метод PUT</button>
--%>

</body>
</html>