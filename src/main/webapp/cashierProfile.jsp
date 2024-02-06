<%@page contentType="text/html;charset=UTF-8"%>
<html>
    <head>
        <link href="allStyles.css" rel="stylesheet" type="text/css">
        <title>Працівники ZLAGODA</title>
    </head>
    <body>
        <%@ include file="cashierNavBar.jsp"%>
        <div class = "background">
            <table>
                <thead>
                    <tr>
                        <th>id</th>
                        <th>прізвище</th>
                        <th>ім'я</th>
                        <th>по-батькові</th>
                        <th>посада</th>
                        <th>дата поч. роботи</th>
                        <th>ДН</th>
                        <th>тел.</th>
                        <th>місто</th>
                        <th>вул.</th>
                        <th>індекс</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>1</td>
                        <td>Письменний</td>
                        <td>Антон</td>
                        <td>Костянтинович</td>
                        <td>вантажник</td>
                        <td>22.09.2015</td>
                        <td>22.03.1996</td>
                        <td>+380980880760</td>
                        <td>Київ</td>
                        <td>просп. Маршала Рокосовського</td>
                        <td>04202</td>
                    </tr>
                </tbody>
            </table>
        </div>
    </body>
</html>