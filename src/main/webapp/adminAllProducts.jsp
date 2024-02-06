<%@page contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <link href="${pageContext.request.contextPath}/allStyles.css" rel="stylesheet" type="text/css">
        <title>Товари ZLAGODA</title>
        <script type="text/javascript">
            function lastCheckProduct(num){
                console.log(num);
                if(confirm("ви дійсно хочете видалити цей продукт?")){
                    location.href="<%=request.getContextPath()%>/deleteproduct?id_product="+num;
                }
            }
            function lastCheckStoreProduct(num){
                console.log(num);
                if(confirm("ви дійсно хочете видалити цей продукт з магазину?")){
                    location.href="<%=request.getContextPath()%>/deletestoreproduct?UPC="+num;
                }
            }
        </script>
    </head>
    <body>
        <%@ include file="adminNavBar.jsp"%>
        <div class = "background">
            <%@ include file="adminProductFilters.jsp"%>
            <button onclick="location.href='<%=request.getContextPath()%>/newproduct'" class = "button">додати тип товару</button>
            <button onclick="location.href='<%=request.getContextPath()%>/newstoreproduct'" class = "button">додати товар до магазину</button>
            <c:forEach var="product" items="${listProducts}">
                <div class="form" style="margin:10px; width: 80%;">
                    <div style="font-size: 30px;margin-left:10px">товар:</div>
                    <table style="background-color: #393e46;">
                        <thead>
                            <tr>
                                <th>id</th>
                                <th>назва</th>
                                <th>характеристики</th>
                                <th>id категорії</th>
                                <th>дії</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td><c:out value="${product.getIdProduct()}"/></td>
                                <td><c:out value="${product.getProductName()}"/></td>
                                <td><c:out value="${product.getCharacteristics()}"/></td>
                                <td><a href="<%=request.getContextPath()%>/categories" class="refer"><c:out value="${product.getCategoryNumber()}"/></a></td>
                                <td>
                                    <button onclick="javascript:lastCheckProduct('${product.getIdProduct()}')" class="button">видалити</button>
                                    <button onclick="document.location.href='<%=request.getContextPath()%>/editproduct?id_product=${product.getIdProduct()}'" class="button">змінити</button>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <div style="font-size: 30px;margin-left:10px">в магазинах:</div>
                    <table style="background-color: #393e46;">
                        <thead>
                            <tr>
                                <th>upc товару</th>
                                <th>ціна</th>
                                <th>к-сть одиниць</th>
                                <th>акційний</th>
                                <th>дії</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="storeproduct" items="${listStoreProduct}">
                                <c:if test="${product.getIdProduct().equals(storeproduct.getIdProduct())}">
                                    <tr>
                                        <td><c:out value="${storeproduct.getUPC()}"/></td>
                                        <td><c:out value="${storeproduct.getSellingPrice()}"/></td>
                                        <td><c:out value="${storeproduct.getProductsNumber()}"/></td>
                                        <td><c:out value="${storeproduct.isPromotionalProduct()}"/></td>
                                        <td>
                                            <button onclick="javascript:lastCheckStoreProduct('${storeproduct.getUPC()}')" class="button">видалити</button>
                                            <button onclick="location.href='<%=request.getContextPath()%>/editstoreproduct?UPC=${storeproduct.getUPC()}'" class="button">змінити</button>
                                        </td>
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