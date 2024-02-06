<%@page contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <link href="allStyles.css" rel="stylesheet" type="text/css">
        <title>Клієнти ZLAGODA</title>
    </head>
    <body>
        <%@ include file="adminNavBar.jsp"%>
        <div class = "background" style="height: 1000px;">
        <div class="form" style="margin:10px; padding:10px; text-align: center;">
            <form class="item" action="customercardspercent">
                <label for="percent">Шукати за відсотком</label><br>
                <input name="percent" type="number" min="0" max="100" class="input" placeholder="відсоток">
                <button class="button" type="submit">застосувати</button>
            </form>
            <div class="item">
                <button onclick="location.href='<%=request.getContextPath()%>/customercards'" class = "button">скинути фільтри</button>
            </div>
        </div>
        <button onclick="location.href='<%=request.getContextPath()%>/newcustomercard'" class = "button">додати</button>
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
                            <button onclick="location.href='<%=request.getContextPath()%>/editcustomercard?card_number=${customerCard.getCardNumber()}'" class="button">змінити</button>
                        </td>
                    </tr>
                    </c:forEach>
                </tbody>
                <script type="text/javascript">
                    function lastCheck(num){
                        console.log(num);
                        if(confirm("ви дійсно хочете видалити цю карту клієнта?")){
                        location.href="<%=request.getContextPath()%>/deletecustomercard?card_number="+num;
                        }
                    }
                </script>
            </table>
        </div>
    </body>
</html>