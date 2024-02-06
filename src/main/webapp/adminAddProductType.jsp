<%@page contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <link href="allStyles.css" rel="stylesheet" type="text/css">
        <title>Редагування товарів ZLAGODA</title>
    </head>
    <body>
        <div class = "background">
            <%@ include file="adminNavBar.jsp"%>
            <h1 style="text-align:center">редагування товарів</h1>
            <c:if test="${product==null}">
                <form class="form" action="insertproduct" method="post">
            </c:if>
            <c:if test="${product!=null}">
                <form class="form" action="updateproduct" method="post">
            </c:if>
                <div style="text-align: center;">
                    <input type="hidden"  value="${product.getIdProduct()}" name="id_product"><br>
                    <label for="product_name">назва:</label><br>
                    <input type="text" value="${product.getProductName()}" name="product_name" placeholder="назва" class="input"><br>
                    <label for="characteristics">характеристики:</label><br>
                    <input type="text" value="${product.getCharacteristics()}" name="characteristics" placeholder="характеристики" class="input"><br>
                    <label for="category_number"><a href="<%=request.getContextPath()%>/categories">категорія:</a></label><br>
                    <input type="number" value="${product.getCategoryNumber()}" name="category_number" placeholder="категорія" class="input"><br>
                    <button class="button" type="submit" style="height: 40px; width:150px">застосувати зміни</button>
                </div>
            </div>
        </div>
    </body>
</html>