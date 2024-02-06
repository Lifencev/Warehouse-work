<%@page contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <link href="allStyles.css" rel="stylesheet" type="text/css">
        <title>Всі чеки ZLAGODA</title>
    </head>
    <body>
        <%@ include file="adminNavBar.jsp"%>
        <div class = "background" style="height: 1000px;">
            <div class="form" style="margin-top:10px; padding:5px; text-align: center;">
                <h3>фільтри</h3>
                <form action="checksbydate">
                    <label for="from_date">з</label>
                    <input name="from_date" type="date" class="input">
                    <label for="to_date">по</label>
                    <input name="to_date" type="date" class="input"><br>
                    <button class="button" type="submit">застосувати</button><br>
                    <label for="id_employee">з номером касира</label>
                    <input class="input" name="id_employee"><br>
                    <button class="button" formaction="checksbydatebycashier" type="submit">застосувати</button>
                </form>
                <button onclick="location.href='<%=request.getContextPath()%>/checks'" class="button">скинути фільтри</button>
            </div>
            <table>
                <thead>
                    <tr>
                        <th>номер</th>
                        <th>дата</th>
                        <th>сума</th>
                        <th>ПДВ</th>
                        <th>касир</th>
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
                        <td><a class="refer" href="<%=request.getContextPath()%>/employees"><c:out value="${check.getIdEmployee()}"/></a></td>
                        <td><a class="refer" href="<%=request.getContextPath()%>/customercards"><c:out value="${check.getCardNumber()}"/></a></td>
                        <td>
                            <button onclick="javascript:lastCheck('${check.getCheckNumber()}')" class="button">видалити</button>
                            <button onclick="location.href='<%=request.getContextPath()%>/checkinfo?check_number=${check.getCheckNumber()}'" class="button">вміст</button>
                        </td>
                    </tr>
                    </c:forEach>
                </tbody>
                <script type="text/javascript">
                    function lastCheck(num){
                        console.log(num);
                        if(confirm("ви дійсно хочете видалити цей чек?")){
                            location.href="<%=request.getContextPath()%>/deletecheck?check_number="+num;
                        }
                    }
                </script>
            </table>
        </div>
    </body>
</html>