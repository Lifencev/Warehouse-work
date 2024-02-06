<%@page contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <link href="allStyles.css" rel="stylesheet" type="text/css">
        <title>Категорії товарів ZLAGODA</title>
    </head>
    <body>
       <%@ include file="cashierNavBar.jsp"%>
        <div class = "background" style="height: 1000px;">
            <table style="width: 50%;">
                <thead>
                    <tr>
                        <th>UPC товару</th>
                        <th>кількість товару</th>
                        <th>ціна</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="sale" items="${listSales}">
                        <tr>
                            <td><a class="refer" href="<%=request.getContextPath()%>/cproducts"><c:out value="${sale.getUPC()}"/></a></td>
                            <td><c:out value="${sale.getProductNumber()}"/></td>
                            <td><c:out value="${sale.getSellingPrice()}"/><td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>