<%@page contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <link href="allStyles.css" rel="stylesheet" type="text/css">
        <title>Редагування товарів у магазині ZLAGODA</title>
    </head>
    <body>
        <div class = "background">
            <%@ include file="adminNavBar.jsp"%>
            <h1 style="text-align:center">редагування товарів у магазині</h1>
            <c:if test="${store_product==null}">
                <form class="form" action="insertstoreproduct" method="post">
            </c:if>
            <c:if test="${store_product!=null}">
                <form class="form" action="updatestoreproduct" method="post">
            </c:if>
                <div style="text-align: center;">
                    <input value="${store_product.getUPC()}" type="hidden" name="UPC_old" placeholder="upc" class="input"><br>
                    <label for="UPC">upc:</label><br>
                    <input value="${store_product.getUPC()}" name="UPC" placeholder="upc" class="input"><br>
                    <label for="UPC_prom">upc акційного:</label><br>
                    <input value="${store_product.getUPCProm()}" name="UPC_prom" placeholder="upc акційного" class="input"><br>
                    <label for="id_product"><a class="refer" href="<%=request.getContextPath()%>/products">id продукту:</a></label><br>
                    <input value="${store_product.getIdProduct()}" name="id_product" type="number" placeholder="id продукту" class="input"><br>
                    <label for="selling_price">ціна:</label><br>
                    <input type="number" value="${store_product.getSellingPrice()}" min="0" value="0" step=".01" name="selling_price" placeholder="ціна" class="input"><br>
                    <label for="products_number">кількість одиниць:</label><br>
                    <input type="number" name="products_number" min="0" value="${store_product.getProductsNumber()}" placeholder="кількість" class="input"><br>
                    <label for="promotional_product">акційний</label>
                    <input type="checkbox" class="checkbox" value="true" name="promotional_product"><br>
                    <button class="button" type="submit" style="height: 40px; width:150px">застосувати зміни</button>
                </div>
            </form>
        </div>
    </body>
</html>