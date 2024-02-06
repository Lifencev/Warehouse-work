<%@page contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <link href="allStyles.css" rel="stylesheet" type="text/css">
        <title>Запити ZLAGODA</title>
    </head>
    <body>
        <%@ include file="adminNavBar.jsp"%>
        <div class = "background">
        <h1>Результат виконання запиту групування</h1>
            <table>
                <thead>
                    <tr>
                        <th>id</th>
                        <th>прізвище</th>
                        <th>ім'я</th>
                        <th>місто</th>
                        <th>Загальна сума</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="count" items="${result}">
                        <tr>
                            <td><c:out value="${count.getId()}"/></td>
                            <td><c:out value="${count.getSurname()}"/></td>
                            <td><c:out value="${count.getName()}"/></td>
                            <td><c:out value="${count.getCity()}"/></td>
                            <td><c:out value="${count.getSumTotal()}"/></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>