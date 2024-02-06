<%@page contentType="text/html;charset=UTF-8"%>
<html>
    <ul style="display:flex;">
        <li><a href="login.jsp">вийти</a></li>
        <li><a href="<%=request.getContextPath()%>/products">всі товари</a></li>
        <li><a href="<%=request.getContextPath()%>/categories">категорії</a></li>
        <li><a href="<%=request.getContextPath()%>/checks">чеки</a></li>
        <li><a href="<%=request.getContextPath()%>/customercards">клієнти</a></li>
        <li><a href="<%=request.getContextPath()%>/employees">робітники</a></li>
        <li><a href="<%=request.getContextPath()%>/queryprep">дод. запити</a></li>
   </ul>
   <div class="logo">
        <div style="text-align:center">ZLAGODA</div>
   </div>
</html>