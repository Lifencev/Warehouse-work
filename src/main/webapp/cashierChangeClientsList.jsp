<%@page contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <link href="allStyles.css" rel="stylesheet" type="text/css">
        <title>Редагування клієнтів</title>
    </head>
    <body>
        <div class = "background">
            <%@ include file="cashierNavBar.jsp"%>
            <h1 style="text-align:center">редагування списку клієнтів</h1>
            <c:if test="${customerCard==null}">
                <h2>Новий клієнт</h2>
                <form class="form" action="cinsertcustomercard" method="post">
            </c:if>
            <c:if test="${customerCard!=null}">
                <h2>Редагування картки номер "${customerCard.getCardNumber()}"</h2>
                <form class="form" action="cupdatecustomercard" method="post">
            </c:if>
                <div style="text-align: center;">
                <label for="card_number">номер картки:</label><br>
                    <input type="hidden" value="${customerCard.getCardNumber()}" name="card_number_old">
                    <input type="text" value="${customerCard.getCardNumber()}" name="card_number" placeholder="номер" class="input"><br>
                    <label for="cust_surname">прізвище:</label><br>
                    <input type="text" value="${customerCard.getCustSurname()}" name="cust_surname" placeholder="прізвище" class="input"><br>
                    <label for="cust_name">ім'я:</label><br>
                    <input type="text" value="${customerCard.getCustName()}"name="cust_name" placeholder="ім'я" class="input"><br>
                    <label for="cust_patronymic">по-батькові:</label><br>
                    <input type="text" value="${customerCard.getCustPatronymic()}" name="cust_patronymic" placeholder="по-батькові" class="input"><br>
                    <label for="phone_number">контактний телефон:</label><br>
                    <input type="tel"value="${customerCard.getPhoneNumber()}" name="phone_number" placeholder="номер" class="input"><br>
                    <label for="city">місто:</label><br>
                    <input type="text" value="${customerCard.getCity()}" name="city" placeholder="місто" class="input"><br>
                    <label for="street">вулиця:</label><br>
                    <input type="text" name="street" value="${customerCard.getStreet()}" placeholder="вулиця" class="input"><br>
                    <label for="zip_code">поштовий індекс:</label><br>
                    <input type="text" name="zip_code" value="${customerCard.getZipCode()}" placeholder="індекс" class="input"><br>
                    <label for="percent">відсоток знижки:</label><br>
                    <input name="percent" type="number" min="0" value="${customerCard.getPercent()}" max="100" class="input" placeholder="відсоток"><br>
                    <button class="button" type="submit" style="height: 40px; width:150px">застосувати зміни</button>
                </div>
            </form>
        </div>
    </body>
</html>
