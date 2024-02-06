<%@page contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <link href="allStyles.css" rel="stylesheet" type="text/css">
        <title>Всі чеки ZLAGODA</title>
    </head>
    <body>
        <%@ include file="cashierNavBar.jsp"%>
        <div class = "background" style="height: 1000px;">
            <div class="form" style="margin-top:10px; padding:5px; text-align: center;">
                <h3>фільтри</h3>
                <label for="since">з</label>
                <input name="since" type="date" class="input">
                <label for="till">по</label>
                <input name="till" type="date" class="input"><br>
                <button class="button">застосувати</button>
            </div>
            <button onclick="location.href='<%=request.getContextPath()%>/newcheck'"class="button">додати</button>
            <table>
                <thead>
                    <tr>
                        <th>номер</th>
                        <th>дата</th>
                        <th>сума</th>
                        <th>ПДВ</th>
                        <th>клієнт</th>
                        <th>дії</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="check" items="${listChecks}">
                        <tr>
                            <td><c:out value="${check.getCheckNumber()}"/></td>
                            <td><c:out value="${check.getPrintDate()}"/></td>
                            <td><c:out value="${check.getSumTotal()}"/></td>
                            <td><c:out value="${check.getVat()}"/></td>
                            <td><a class="refer" href="<%=request.getContextPath()%>/customercards"><c:out value="${check.getCardNumber()}"/></a></td>
                            <td><button onclick="location.href='<%=request.getContextPath()%>/ccheckinfo?check_number=${check.getCheckNumber()}'" class="button">вміст</button></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>