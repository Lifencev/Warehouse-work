<%@page contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <link href="${pageContext.request.contextPath}/allStyles.css" rel="stylesheet" type="text/css">
        <title>Категорії товарів ZLAGODA</title>
    </head>
    <body>
        <%@ include file="adminNavBar.jsp"%>
        <div class = "background" style="height: 1000px;">
        <button onclick="location.href='<%=request.getContextPath()%>/newcategory'" class = "button">додати</button>
            <table style="width: fit-content;">
                <thead>
                    <tr>
                        <th>id</th>
                        <th>назва</th>
                        <th>дії</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="category" items="${listCategories}">
                        <tr>
                            <td><c:out value="${category.categoryNumber}"/></td>
                            <td><c:out value="${category.categoryName}"/></td>
                            <td><button onclick="javascript:lastCheck('${category.getCategoryNumber()}')" class="button">видалити</button>
                                <button class="button" onclick="location.href='<%=request.getContextPath()%>/editcategory?category_number=${category.getCategoryNumber()}'">змінити</button></td>
                        </tr>
                    </c:forEach>
                </tbody>
                <script type="text/javascript">
                    function lastCheck(num){
                        console.log(num);
                        if(confirm("ви дійсно хочете видалити цю категорію?")){
                            location.href="<%=request.getContextPath()%>/deletecategory?category_number="+num;
                        }
                    }
                </script>
            </table>
        </div>
    </body>
</html>