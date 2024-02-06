<%@page contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <link href="allStyles.css" rel="stylesheet" type="text/css">
        <title>Клієнти ZLAGODA</title>
    </head>
    <body>
        <%@ include file="cashierNavBar.jsp"%>
        <div class = "background">
        <form action="ccustomercardssurname" class="form" style="margin:10px; padding:10px; text-align: center;">
            <label for="cust_surname">Шукати за прізвищем</label><br>
            <input name="cust_surname" class="input" placeholder="прізвище">
            <button class="button" type="submit">застосувати</button>
        </form>
        <div class="item">
            <button onclick="location.href='<%=request.getContextPath()%>/ccustomercards'" class = "button">скинути фільтри</button>
        </div>
        <button onclick="location.href='<%=request.getContextPath()%>/cnewcustomercard'" class = "button">додати</button>
            <table>
                <thead>
                    <tr>
                        <th>номер карти</th>
                        <th>прізвище</th>
                        <th>ім'я</th>
                        <th>по-батькові</th>
                        <th>тел.</th>
                        <th>місто</th>
                        <th>вул.</th>
                        <th>індекс</th>
                        <th>відсоток</th>
                        <th>дії</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="customerCard" items="${listCustomerCards}">
                    <tr>
                        <td><c:out value="${customerCard.getCardNumber()}"/></td>
                        <td><c:out value="${customerCard.getCustSurname()}"/></td>
                        <td><c:out value="${customerCard.getCustName()}"/></td>
                        <td><c:out value="${customerCard.getCustPatronymic()}"/></td>
                        <td><c:out value="${customerCard.getPhoneNumber()}"/></td>
                        <td><c:out value="${customerCard.getCity()}"/></td>
                        <td><c:out value="${customerCard.getStreet()}"/></td>
                        <td><c:out value="${customerCard.getZipCode()}"/></td>
                        <td><c:out value="${customerCard.getPercent()}"/></td>
                        <td>
                            <button onclick="javascript:lastCheck('${customerCard.getCardNumber()}')" class="button">видалити</button>
                            <button onclick="location.href='<%=request.getContextPath()%>/ceditcustomercard?card_number=${customerCard.getCardNumber()}'" class="button">змінити</button>
                        </td>
                    </tr>
                    </c:forEach>
                </tbody>
                <script type="text/javascript">
                    function lastCheck(num){
                        console.log(num);
                        if(confirm("ви дійсно хочете видалити цю карту клієнта?")){
                        location.href="<%=request.getContextPath()%>/cdeletecustomercard?card_number="+num;
                        }
                    }
                </script>
            </table>
        </div>
    </body>
</html>