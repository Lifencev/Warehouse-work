<%@page contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <div class="form" style="margin:10px; padding:10px; text-align: center;">
        <h3>фільтри</h3>
        <form class="item" action="cproductswcategory">
            <label for="category_number">Шукати за <a class="refer" href="<%=request.getContextPath()%>/categories">категорією</a></label><br>
            <input type="number" name="category_number" class="input">
            <button class="button" type="submit">застосувати</button>
        </form>
        <form class="item" action="cproductswname">
            <label for="product_name">Шукати за назвою</label><br>
            <input name="product_name" class="input">
            <button class="button" type="submit">застосувати</button>
        </form>
        <form class="item" action="cstoreproductswsalewqty">
            <label for="bqty">сортувати за кількістю</label>
            <input name="bqty" type="checkbox" value="true" class="checkbox"><br>
            <label for="sale">чи акційний?</label>
            <input name="sale" type="checkbox" value="true" class="checkbox"><br>
            <button class="button" type="submit">застосувати</button>
        </form>
        <form class="item" action="cstoreproductswupc">
            <label for="UPC">upc</label><br>
            <input type="number" name="UPC" class="input" placeholder="upc"><br>
            <button type="submit" class="button">застосувати</button><br>
        </form>
        <button class="button" onclick="location.href='<%=request.getContextPath()%>/cproducts'">скинути фільтри</button>
    </div>
</html>