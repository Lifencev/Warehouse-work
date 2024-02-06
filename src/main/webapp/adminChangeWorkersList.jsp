<%@page contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <link href="allStyles.css" rel="stylesheet" type="text/css">
        <title>Редагування працівників</title>
    </head>
    <body>
        <div class = "background">
            <%@ include file="adminNavBar.jsp"%>
            <h1 style="text-align:center">редагування списку працівників</h1>
            <c:if test="${employee==null}">
                <h2>Новий робітник</h2>
                <form class="form" action="insertemployee" method="post">
            </c:if>
            <c:if test="${employee!=null}">
                <h2>Редагування робітника номер "${employee.getIdEmployee()}"</h2>
                <form class="form" action="updateemployee" method="post">
            </c:if>            
                <div style="text-align: center;">
                    <input type="hidden" value="${employee.getIdEmployee()}" name="id_employee_old" placeholder="id" class="input"><br>
                    <label for="id_employee">id:</label><br>
                    <input type="text" value="${employee.getIdEmployee()}" name="id_employee" placeholder="id" class="input"><br>
                    <label for="empl_surname">прізвище:</label><br>
                    <input type="text" value="${employee.getEmplSurname()}" name="empl_surname" placeholder="прізвище" class="input"><br>
                    <label for="empl_name">ім'я:</label><br>
                    <input type="text" name="empl_name" value="${employee.getEmplName()}" placeholder="ім'я" class="input"><br>
                    <label for="empl_patronymic">по-батькові:</label><br>
                    <input type="text" value="${employee.getEmplPatronymic()}" name="empl_patronymic" placeholder="по-батькові" class="input"><br>
                    <label for="empl_role">посада:</label><br>
                    <input type="text" name="empl_role" value="${employee.getEmplRole()}" placeholder="посада" class="input"><br>
                    <label for="salary">заробітна плата:</label><br>
                    <input type="number" min="0" name="salary" value="${employee.getSalary()}" placeholder="зарплатня" class="input"><br>
                    <label for="date_of_start">дата початку роботи:</label><br>
                    <input type="date" min="10-10-1000" max="10-10-2100" value="${employee.getDateOfStart()}"  name="date_of_start" class="input"><br>
                    <label for="date_of_birth">день народження:</label><br>
                    <input type="date" min="10-10-1000" max="10-10-2100" value="${employee.getDateOfBirth()}" name="date_of_birth" class="input"><br>
                    <label for="phone_number">контактний телефон:</label><br>
                    <input type="tel" name="phone_number" value="${employee.getPhoneNumber()}" class="input"><br>
                    <label for="city">місто:</label><br>
                    <input type="text" value="${employee.getCity()}" name="city" class="input"><br>
                    <label for="street">вулиця:</label><br>
                    <input type="text" name="street" value="${employee.getStreet()}" class="input"><br>
                    <label for="zip_code">поштовий індекс:</label><br>
                    <input type="text" name="zip_code" value="${employee.getZipCode()}" class="input"><br>
                    <button class="button" type="submit" style="height: 40px; width:150px">застосувати зміни</button>
                </div>
            </form>
        </div>
    </body>
</html>
