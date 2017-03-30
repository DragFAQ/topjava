<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <title>Add new / Edit meal</title>
</head>
<body>
<form method="POST" action='meals' name="frmAddMeal">
    Meal ID : <input type="text" readonly="readonly" name="id"
                     value="${meal.id}" /> <br />
    Date : <input
        type="datetime" name="dateTime"
        value="<fmt:formatDate value="${meal.dateTimeAsDate}" pattern="yyyy-MM-dd HH:mm" />" /> <br />
    Description : <input
        type="text" name="description"
        value="${meal.description}" /> <br />
    Calories : <input
        type="text" name="calories"
        value="${meal.calories}" /> <br />
    <input
            type="submit" value="Submit" />
</form>
</body>
</html>
