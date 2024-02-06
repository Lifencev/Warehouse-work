<%@page contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <link href="${pageContext.request.contextPath}/allStyles.css" rel="stylesheet" type="text/css">
        <title>Товари ZLAGODA</title>
    </head>
    <body>
        <%@ include file="cashierNavBar.jsp"%>
        <div class = "background" style="height: 1000px;">
            <%@ include file="cashierProductFilters.jsp"%>
            <c:forEach var="storeproduct" items="${listStoreProduct}">
                <div class="form" style="margin:10px; width: 80%;">
                    <div style="font-size: 30px;margin-left:10px">товар у магазині:</div>
                    <table style="background-color: #393e46;">
                        <thead>
                            <tr>
                                <th>upc товару</th>
                                <th>ціна</th>
                                <th>к-сть одиниць</th>
                                <th>акційний</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td><c:out value="${storeproduct.getUPC()}"/></td>
                                <td><c:out value="${storeproduct.getSellingPrice()}"/></td>
                                <td><c:out value="${storeproduct.getProductsNumber()}"/></td>
                                <td><c:out value="${storeproduct.isPromotionalProduct()}"/></td>
                            </tr>
                        </tbody>
                    </table>
                    <div style="font-size: 30px;margin-left:10px">детальніше про товар:</div>
                    <table style="background-color: #393e46;">
                        <thead>
                            <th>id</th>
                            <th>назва</th>
                            <th>характеристики</th>
                            <th>id категорії</th>
                        </thead>
                        <tbody>
                            <c:forEach var="product" items="${listProducts}">
                                <c:if test="${product.getIdProduct().equals(storeproduct.getIdProduct())}">
                                    <tr>
                                    <td><c:out value="${product.getIdProduct()}"/></td>
                                    <td><c:out value="${product.getProductName()}"/></td>
                                    <td><c:out value="${product.getCharacteristics()}"/></td>
                                    <td><a href="<%=request.getContextPath()%>/categories" class="refer"><c:out value="${product.getCategoryNumber()}"/></a></td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:forEach>
        </div>
    </body>
</html>