<%-- 
    Document   : message
    Created on : 2020-11-2, 23:47:23
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
        <title>用户个人消息</title>
        <style>
            table{
                width: 960px;
                border: black solid 1px;
                border-collapse: collapse;
            }
            .td_style{
                width:100px;
                height: 50px;
                border: black solid 1px;
                text-align:center;
                vertical-align:middle;
            }
            .td_style2{
                width: 100px;
                height: 300px;
            }
        </style>
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
    </body>

    <!--未登录无法访问该界面-->
    <%
        if (user == null) {
    %><br/>请登录后再访问该界面！3秒后页面将跳转至登录界面<%
            response.setHeader("refresh", "3;URL =login.jsp");
        }
    %>


    <!--输出界面-->
    <table>
        <tr>
            <td class="td_style">
                <a href="message.jsp">所有消息</a>
            </td>
            <td rowspan="7" ALIGN="left" VALIGN="top">
                <!--控制页面显示-->
                <%
                    ArrayList messageList = new ArrayList();
                    message_dao messageDAO = new message_dao();

                    /*获取URL的值*/
                    String menu = "";
                    menu = request.getParameter("menu");

                    if (menu == null || menu.equals("")) {
                        messageList = messageDAO.getMessage(user.getId());
                        if (messageList.isEmpty()) {
                %>您当前没有消息<%
                } else {
                    for (int i = 0; i < messageList.size(); i++) {
                        message one_message = new message();
                        one_message = (message) messageList.get(i);
                %>
                <hr/>
                <%
                    /*如果是私信消息，则打印出这是私人消息*/
                    if(one_message.getType() == 3){
                        out.print("这是一条私人消息，请到私人信箱查看<br/>");
                    }
                    out.println(one_message.getContent());
                    out.println("<br/>时间:" + one_message.getCreate_time());
                %>
                <hr/>
                <%
                        }
                    }
                } else if (menu.equals("system")) {
                    /*消息类型：0系统，1回帖，2点赞，3私信*/
                    messageList = messageDAO.getMessageListWithType(user.getId(), 0);
                    if (messageList.isEmpty()) {
                %>您当前没有消息<%
                } else {
                    for (int i = 0; i < messageList.size(); i++) {
                        message one_message = new message();
                        one_message = (message) messageList.get(i);
                %>
                <hr/>
                <%
                    out.print(one_message.getContent());
                    out.println("<br/>时间:" + one_message.getCreate_time());
                %>
                <hr/>
                <%
                        }
                    }
                } else if (menu.equals("reply")) {
                    /*消息类型：0系统，1回帖，2点赞，3私信*/
                    messageList = messageDAO.getMessageListWithType(user.getId(), 1);
                    if (messageList.isEmpty()) {
                %>您当前没有消息<%
                } else {
                    for (int i = 0; i < messageList.size(); i++) {
                        message one_message = new message();
                        one_message = (message) messageList.get(i);
                %>
                <hr/>
                <%
                    out.print(one_message.getContent());
                    out.println("<br/>时间:" + one_message.getCreate_time());
                %>
                <hr/>
                <%
                        }
                    }
                } else if (menu.equals("private")) {
                    /*消息类型：0系统，1回帖，2点赞，3私信*/
                    messageList = messageDAO.getMessageListWithType(user.getId(), 3);
                    if (messageList.isEmpty()) {
                %>您当前没有消息<%
                } else {
                    for (int i = 0; i < messageList.size(); i++) {
                        message one_message = new message();
                        one_message = (message) messageList.get(i);
                %>
                <hr/>
                消息发送者:<a href="userInfo.jsp?uid=<%=one_message.getSender_id()%>">
                    <%=one_message.getUsername()%>用户ID:<%=one_message.getSender_id()%>
                </a><br/>
                <%
                    out.print(one_message.getContent());
                    out.println("<br/>时间:" + one_message.getCreate_time());
                %>
                <hr/>
                <%
                        }
                    }
                } else if (menu.equals("posted")) {
                    /*我发送的消息*/
                    messageList = messageDAO.getMyPostedMessage(user.getId());
                    if (messageList.isEmpty()) {
                %>您当前没有消息<%
                } else {
                    for (int i = 0; i < messageList.size(); i++) {
                        message one_message = new message();
                        one_message = (message) messageList.get(i);
                %>
                <hr/>
                <%
                    out.print("<a href=\"userInfo.jsp?uid="+one_message.getReceiver_id()+"\">" );
                    out.print("收件人:"+one_message.getTarget_username()+"</a><br/>");                   
                    out.print(one_message.getContent());
                    out.println("<br/>时间:" + one_message.getCreate_time());
                %>
                <hr/>
                <%
                        }
                    }
                } else if (menu.equals("send")) {
                    /*发送消息界面*/
                %>
                <br/>
                <form action="sendMessage_process">
                    收件人的邮箱：<input type="text" name="userEmail"/><br/><br/>
                    消息内容:<br/>&nbsp;
                    <textarea name="content" rows="5" cols="100" style="resize: none"></textarea><br/><br/>
                    <input type="submit" value="发送"/>
                </form>
                <%
                    }

                %>
            </td>
        </tr>
        <tr class="td_style">
            <td>
                <a href="message.jsp?menu=system">系统消息</a>
            </td>
        </tr>
        <tr class="td_style">
            <td>
                <a href="message.jsp?menu=reply">回复通知</a>
            </td>
        </tr>
        <tr class="td_style">
            <td>
                <a href="message.jsp?menu=private">私人消息</a>
            </td>
        </tr>
        <tr class="td_style">
            <td>
                <a href="message.jsp?menu=posted">我发送出的消息</a>
            </td>
        </tr>
        <tr class="td_style">
            <td>
                <a href="message.jsp?menu=send">发送消息</a>
            </td>
        </tr>

        <!--留空-->
        <tr>
            <td class="td_style2"></td>
        </tr>
    </table>
</html>
