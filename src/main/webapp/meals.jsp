<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css" integrity="sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ" crossorigin="anonymous">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js" integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn" crossorigin="anonymous"></script>

    <title>Meal list</title>
</head>
<body>
<table class="table table-striped display dataTable no-footer">
    <thead>
    <tr><th>Дата и время</th><th>Описание</th><th>Калории</th><th></th><th></th></tr>
    </thead>
    <tbody>
    <c:forEach items="${meals}" var="meal">
        <c:choose>
            <c:when test="${meal.exceed}">
                <tr style="color: #ff0000">
            </c:when>
            <c:otherwise>
                <tr style="color: #00ff00">
            </c:otherwise>
        </c:choose>
            <td><c:out value="${meal.dateTime}" /></td>
            <td><c:out value="${meal.description}" /></td>
            <td><c:out value="${meal.calories}" /></td>
            <td>
                <a href="meals?action=edit&id=<c:out value="${meal.id}"/>">Update</a>
            </td>
            <td>
                <a href="meals?action=delete&id=<c:out value="${meal.id}"/>">Delete</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
