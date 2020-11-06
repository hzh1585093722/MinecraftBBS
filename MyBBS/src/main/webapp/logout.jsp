<%-- 
    Document   : logout
    Created on : 2020-11-3, 19:29:46
    Author     : 15850
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>注销页面</title>
    </head>
    <body>
        <%
            /*清掉session中的用户信息*/
            request.getSession().removeAttribute("users");
            response.sendRedirect("index.jsp");
        %>
    </body>
</html>
