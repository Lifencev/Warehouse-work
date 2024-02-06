<%@page contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <link href="allStyles.css" rel="stylesheet" type="text/css">
        <title>Редагування категорій ZLAGODA</title>
    </head>
    <body>
        <div class = "background">
            <%@ include file="adminNavBar.jsp"%>
            <h1 style="text-align:center">редагування категорій</h1>
            <c:if test="${category ==null}">
                <form class="form" action="insertcategory" method="post">
            </c:if>
            <c:if test="${category !=null}">
                <form class="form" action="updatecategory" method="post">
            </c:if>
                <div style="text-align: center;">
                    <c:if test="${category != null}">
                        <input class="input" style="width:fit-content;" type="hidden" name="category_number" value="<c:out value='${category.getCategoryNumber()}'/>"><br>
                        <label for="name">нова назва категорії "${category.getCategoryNumber()}":</label><br>
                    </c:if>
                    <c:if test="${category ==null}">
                        <label for="name">назва нової категорії:</label><br>
                    </c:if>
                    <input type="text" name="category_name" value="<c:out value='${category.getCategoryName()}'/>" placeholder="назва" class="input"><br>
                    <input class="button" value="застосувати зміни" type="submit" style="height: 40px; width:150px">
                </div>
            </div>
        </div>
    </body>
</html>