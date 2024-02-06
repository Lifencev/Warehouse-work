<%@page contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <link href="${pageContext.request.contextPath}/allStyles.css" rel="stylesheet" type="text/css">
        <title>Товари ZLAGODA</title>
    </head>
    <body>
        <%@ include file="adminNavBar.jsp"%>
        <div class = "background">
            <h2 style="text-align:center;">Підготовча сторінка для складних запитів</h2>
            <form action="customersboughtfromonecategory" class="form">
                <label for="category_name">Назва категорії</label>
                <input name="category_name" class="input">
                <button class="button" type="submit">виконати</button>
            </form>
            <form action="totalsumbycashierfromhiscity" class="form">
                <label>Сума продажу працівником клієнтам з його міста</label>
                <button class="button" type="submit">виконати</button>
            </form>
        </div>
    </body>
</html>