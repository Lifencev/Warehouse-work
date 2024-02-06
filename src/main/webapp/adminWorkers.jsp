<%@page contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <link href="allStyles.css" rel="stylesheet" type="text/css">
        <title>Працівники ZLAGODA</title>
    </head>
    <body>
        <%@ include file="adminNavBar.jsp"%>
        <div class = "background" style="height: 1000px;">
        <div class="form" style="margin:10px; padding:10px; text-align: center;">
            <form class="item" action="employeesbysurname">
                <label for="empl_surname">Шукати за прізвищем</label><br>
                <input name="empl_surname" class="input" placeholder="прізвище">
                <button class="button" type="submit">застосувати</button>
            </form>
            <div class="item">
                <button class="button" onclick="location.href='<%=request.getContextPath()%>/cashiers'">тільки касири</button>
            </div>
            <div class = "item">
                <button class="button" onclick="location.href='<%=request.getContextPath()%>/employees'">скинути фільтри</button>
            </div>
        </div>
        <button onclick="location.href='<%=request.getContextPath()%>/newemployee'"class = "button">додати</button>
            <table>
                <thead>
                    <tr>
                        <th>id</th>
                        <th>прізвище</th>
                        <th>ім'я</th>
                        <th>по-батькові</th>
                        <th>посада</th>
                        <th>заробітна плата</th>
                        <th>дата поч. роботи</th>
                        <th>ДН</th>
                        <th>тел.</th>
                        <th>місто</th>
                        <th>вул.</th>
                        <th>індекс</th>
                        <th>дії</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="employee" items="${listEmployees}">
                    <tr>
                        <td><c:out value="${employee.getIdEmployee()}"/></td>
                        <td><c:out value="${employee.getEmplSurname()}"/></td>
                        <td><c:out value="${employee.getEmplName()}"/></td>
                        <td><c:out value="${employee.getEmplPatronymic()}"/></td>
                        <td><c:out value="${employee.getEmplRole()}"/></td>
                        <td><c:out value="${employee.getSalary()}"/></td>
                        <td><c:out value="${employee.getDateOfBirth()}"/></td>
                        <td><c:out value="${employee.getDateOfStart()}"/></td>
                        <td><c:out value="${employee.getPhoneNumber()}"/></td>
                        <td><c:out value="${employee.getCity()}"/></td>
                        <td><c:out value="${employee.getStreet()}"/></td>
                        <td><c:out value="${employee.getZipCode()}"/></td>
                        <td>
                            <button class="button" onclick="javascript:lastCheck('${employee.getIdEmployee()}')">видалити</button>
                            <button onclick="location.href='<%=request.getContextPath()%>/editemployee?id_employee=${employee.getIdEmployee()}'"class="button">змінити</button>
                        </td>
                    </tr>
                    </c:forEach>
                </tbody>
                <script type="text/javascript">
                    function lastCheck(num){
                        console.log(num);
                        if(confirm("ви дійсно хочете видалити цього працівника?")){
                            location.href="<%=request.getContextPath()%>/deleteemployee?id_employee="+num;
                        }
                    }
                </script>
            </table>
        </div>
    </body>
</html>