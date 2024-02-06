<%@page contentType="text/html;charset=UTF-8"%>
<html>
    <ul style="display:flex;">
        <li><a href="login.jsp">вийти</a></li>
        <li><a href="<%=request.getContextPath()%>/cproducts">всі товари</a></li>
        <li><a href="<%=request.getContextPath()%>/cchecks">чеки</a></li>
        <li><a href="<%=request.getContextPath()%>/ccustomercards">клієнти</a></li>
        <li><a href="cashierProfile.jsp">про мене</a></li>
    </ul>
    <div class="logo">
        <div style="text-align:center">ZLAGODA</div>
    </div>
</html>