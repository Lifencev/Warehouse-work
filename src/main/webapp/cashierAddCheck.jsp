<%@page contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <link href="allStyles.css" rel="stylesheet" type="text/css">
        <title>Новий чек ZLAGODA</title>
        <script type="text/javascript">
            function addItem(){
                let newitem = document.createElement('div');
                newitem.classList.add('item');

                let upcl = document.createElement('label');
                upcl.setAttribute("for", "UPC");
                upcl.innerHTML = "upc:";
                let upci = document.createElement('input');
                upci.setAttribute("name", "UPC");
                upci.setAttribute("placeholder", "upc");
                upci.classList.add('input');

                let qtyl = document.createElement('label');
                qtyl.setAttribute("for", "products_number");
                qtyl.innerHTML = "кількість:";
                let qtyi = document.createElement('input');
                qtyi.setAttribute("name", "products_number");
                qtyi.setAttribute("placeholder", "кількість");
                qtyi.setAttribute("min", "0");
                qtyi.setAttribute("type", "number");
                qtyi.classList.add('input');

                let deletebuttton = document.createElement('button');
                deletebuttton.innerHTML="-";
                deletebuttton.classList.add('button');
                deletebuttton.addEventListener("click", ()=>deleteItem(newitem));

                newitem.appendChild(upcl);
                newitem.appendChild(upci); 
                newitem.appendChild(qtyl);
                newitem.appendChild(qtyi);
                newitem.appendChild(deletebuttton);
                document.getElementById("toAdd").appendChild(newitem);
            }

            function deleteItem(item){
                document.getElementById("toAdd").removeChild(item);
            } 
        </script>
    </head>
    <body>
        <div class = "background">
            <%@ include file="cashierNavBar.jsp"%>
            <h1 style="text-align:center">новий чек</h1>
            <form action="insertcheck" method="post" class="form">
                <div style="text-align: center;">
                    <label for="check_number">номер чека:</label><br>
                    <input name="check_number" placeholder="номер чекка" class="input"><br>
                    <label for="card_number">картка клієнта:</label><br>
                    <input name="card_number" placeholder="картка клієнта" class="input"><br>
                    <label for="id_employee">id працівника</label><br>
                    <input name="id_employee" placeholder="id працівника" class="input"><br>
                    <div id="toAdd">
                        <div class="item">
                            <label for="UPC">upc:</label>
                            <input type="number" name="UPC" placeholder="upc" class="input">
                            <label for="products_number">кількість:</label>
                            <input type="number" name="products_number" min="0" placeholder="кількість" class="input">
                        </div>
                    </div>
                    <button class="button" type="button" onclick="javascript:addItem()" style="width:50px">+</button><br>
                    <button class="button" type="submit" style="height: 40px; width:150px">додати чек</button>
                </div>
            </form>
        </div>
    </body>
</html>