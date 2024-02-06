<%@page contentType="text/html;charset=UTF-8"%>
<html>
    <head>
        <link href="allStyles.css" rel="stylesheet" type="text/css">
        <title>Вхід ZLAGODA</title>
    </head>
    <body>
        <div class = "background">
            <div class="logo">
                <div style="text-align:center">ZLAGODA</div>
            </div>
            <h2 style="text-align:center">ласкаво просимо</h2>
            <h1 style="text-align:center">вхід</h1>
            <div class="form">
                <div style="text-align: center;">
                    <label for="name">ім'я:</label><br>
                    <input type="text" name="name" placeholder="ім'я" class="input"><br>
                    <label for="password">пароль:</label><br>
                    <input type="text" name="password" placeholder="пароль" class="input"><br>
                    <button class="button" onclick="location.href='<%=request.getContextPath()%>/ccustomercards'" style="height: 40px; width:150px">я касир</button>
                    <button class="button" onclick="location.href='<%=request.getContextPath()%>/products'" style="height: 40px; width:150px">я менеджер</button>
                </div>
            </div>
        </div>
    </body>
</html>
