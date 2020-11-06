<%-- 
    Document   : login
    Created on : 2020-11-2, 23:44:31
    Author     : 15850
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bean.*" %>
<%@page import="dao.*" %>
<%@page import="JDBC_connector.DBConn" %>
<%@page import="java.util.ArrayList" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <!--页面头开始-->
        <%
            /*获取session中的用户信息*/
            users user = new users();
            user = (users) request.getSession().getAttribute("users");

            if (user == null) {
        %>
        <a href="login.jsp">
            登录
        </a>&nbsp;&nbsp;
        <a href="register.jsp">
            注册
        </a>
        <br/>
        <%
            /*获取权限0为普通，1版主，2超管*/
        } else if (user.getGroup() == 0) {
        %>
        <a href="InfoManage.jsp">个人信息</a>&nbsp;&nbsp;
        <a href="message.jsp">我的消息</a>&nbsp;&nbsp;
        <a href="logout.jsp">注销</a>
        <%
        } else if (user.getGroup() > 0) {
        %>
        <a href="InfoManage.jsp">个人信息</a>&nbsp;&nbsp;
        <a href="message.jsp">我的消息</a>&nbsp;&nbsp;
        <a href="manage.jsp">论坛管理</a>&nbsp;&nbsp;
        <a href="logout.jsp">注销</a>
        <%
            }
        %>
        <br/><br/><br/>
        <a href="index.jsp">首页</a>&nbsp;&nbsp;
        <a href="tab.jsp?tabname=notice">公告</a>&nbsp;&nbsp;
        <a href="tab.jsp?tabname=FAQs">问答</a>&nbsp;&nbsp;
        <a href="tab.jsp?tabname=MOD">MOD区</a>&nbsp;&nbsp;
        <a href="tab.jsp?tabname=plugin">插件区</a>&nbsp;&nbsp;
        <a href="tab.jsp?tabname=server">服务器区</a>&nbsp;&nbsp;<br/><br/>
        <!--页面头结束-->
        
        <!--登录信息控制-->
        <%
            String loginstatus = request.getParameter("loginstatus");
            if (loginstatus != null && loginstatus.equals("fail"))
                out.println("账号或者密码错误");
        %>
        <form action="login_process">
            邮箱:<input type="text" name="email"/>
            密码:<input type="password" name="password"/>
            <input type="submit" value="提交"/>
        </form>
    </body>
</html>
