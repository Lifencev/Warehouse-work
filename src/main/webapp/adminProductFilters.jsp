<%@page contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <div class="form" style="margin:10px; padding:10px; text-align: center;">
        <h3>фільтри</h3>
        <form class="item" action="productswcategory">
            <label for="category_number">Шукати за <a class="refer" href="<%=request.getContextPath()%>/categories">категорією</a></label><br>
            <input type="number" name="category_number" class="input">
            <button class="button" type="submit">застосувати</button>
        </form>
        <form class="item" action="storeproductswsalewqty">
            <div class="item">
                <label for="bqty">сортувати за кількістю</label>
                <input name="bqty" type="checkbox" value="true" class="checkbox"><br>
                <button class="button" formaction="storeproductswqty" type="submit">застосувати</button>
            </div>
            <label for="sale">чи акційний?</label>
            <input name="sale" type="checkbox" value="true" class="checkbox"><br>
            <button class="button" type="submit">застосувати</button>
        </form>
        <form class="item" action="storeproductswupc">
            <label for="UPC">upc</label><br>
            <input type="number" name="UPC" class="input" placeholder="upc"><br>
            <button type="submit" class="button">застосувати</button><br>
        </form>
        <button class="button" onclick="location.href='<%=request.getContextPath()%>/products'">скинути фільтри</button>
    </div>
</html>