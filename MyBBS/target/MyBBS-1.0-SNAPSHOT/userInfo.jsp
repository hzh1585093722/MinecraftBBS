<%-- 
    Document   : userInfo
    Created on : 2020-11-3, 11:00:45
    Author     : 15850
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bean.users" %>
<%@page import="dao.user_dao" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>用户信息界面</title>
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
        <a href="tab.jsp?tabname=server">服务器区</a>&nbsp;&nbsp;<br/><br/><br/>
        <!--页面头结束-->

        <%
            String uid = request.getParameter("uid");
            if (uid == null) {
                out.println("您访问的用户信息不存在");
            } else {
                user = new users();
                user_dao userDAO = new user_dao();
                user.setId(Integer.parseInt(request.getParameter("uid")));
                /*获取信息*/
                user = userDAO.ViewUserInfo(user);
                if (user.getEmail() == null) {
                    out.println("您访问的用户信息不存在");
                } else {
        %>
        头像<img src="<%=user.getAvatar()%>" width="150px" height="200px"/><br/>
        用户id:<%=user.getId()%><br/>
        邮箱:<%=user.getEmail()%><br/>
        用户名：<%=user.getUsername()%><br/>
        qq:<%=user.getQQ()%><br/>
        性别:<%
            if (user.getSex() == 0) {
        %>秘密<%
        } else if (user.getSex() == 1) {
        %>男<%
        } else if (user.getSex() == 2) {
        %>女<%
            }
        %><br/>
        电话:<%=user.getPhone()%><br/>
        生日：<%=user.getBirth()%><br/>
        账号创建时间:<%=user.getCreate_time()%><br/>
        最后一次上线时间:<%=user.getUpdate_time()%>
        <%
                }
            }
        %>
    </body>
</html>
