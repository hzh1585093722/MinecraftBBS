<%-- 
    Document   : InfoManage
    Created on : 2020-11-3, 19:25:22
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
        <title>用户个人信息管理界面</title>
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

        <table>
            <tr>
                <td colspan="2">用户个人信息界面</td>
            </tr>
            <tr>
                <td class="td_style">
                    <a href="InfoManage.jsp">概要信息</a>                   
                </td>
                <!--控制和输出部分-->
                <td rowspan="5"   ALIGN="left" VALIGN="top">
                    <%
                        /*检查用户是否登录*/
                        if (user == null) {
                            out.print("请登录后再来");
                            response.setHeader("refresh", "3;URL=login.jsp");
                        } else {
                            /*接收传参*/
                            String menu = "";
                            menu = request.getParameter("menu");
                            if (menu == null) {
                                user_dao userDAO = new user_dao();
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
                    } else if (menu.equals("")) {
                        response.sendRedirect("InfoManage.jsp");
                    } else if (menu.equals("update")) {
                        out.print("该功能即将上线");
                    } else if (menu.equals("mytopic")) {
                        /*获取用户的帖子信息*/
                        topic_dao topicDAO = new topic_dao();
                        ArrayList topicList = new ArrayList();
                        topicList = topicDAO.viewTopicListByUserId(user.getId());

                        if (topicList.isEmpty()) {
                            out.print("您暂时还没有发帖");
                        } else {
                            for (int i = 0; i < topicList.size(); i++) {
                                topic one_topic = (topic) topicList.get(i);
                    %>
                    <hr/>
                    <form action="deleteTopic_process">
                        <input type="hidden" name="topicId" value="<%=one_topic.getId()%>"/>
                        标题:<a href="topic.jsp?topicId=<%=one_topic.getId()%>">
                            <%=one_topic.getTitle()%><br/>
                            内容：<%=one_topic.getContent()%><br/>
                            发布时间:<%=one_topic.getCreate_time()%><br/>
                        </a>
                        <input type="submit" value="删除帖子"/>
                    </form>
                    <hr/>
                    <%
                            }

                        }
                    } else if (menu.equals("myreply")) {
                        reply_dao replyDAO = new reply_dao();
                        ArrayList replyList = new ArrayList();

                        replyList = replyDAO.getTopicListByUserId(user.getId());

                        if (replyList.isEmpty()) {
                            out.print("您暂时没有回复");
                        } else {
                            for (int i = 0; i < replyList.size(); i++) {
                                reply one_reply = (reply) replyList.get(i);
                    %>
                    <form action="deleteReply_process">
                        帖子链接:<a href="topic.jsp?topicId=<%=one_reply.getTopic_id()%>">
                            topic.jsp?topicId=<%=one_reply.getTopic_id()%>
                        </a><br/>
                        回复内容:<%=one_reply.getContent()%><br/>
                        回复时间:<%=one_reply.getCreate_time()%><br/>
                        <input type="hidden" name="replyId" value="<%=one_reply.getId()%>"/>
                        <input type="submit" value="删除该回复"/>
                    </form>
                    <%
                                    }
                                }
                            }
                        }

                    %>
                </td>
            </tr>
            <tr>
                <td class="td_style">
                    <a href="InfoManage.jsp?menu=update">信息修改</a>                     
                </td>               
            </tr>
            <tr>
                <td class="td_style">
                    <a href="InfoManage.jsp?menu=mytopic">我的贴子</a>
                </td>                
            </tr>
            <tr>
                <td class="td_style">
                    <a href="InfoManage.jsp?menu=myreply">我的回帖</a>
                </td>                
            </tr>
            <!--留空-->
            <tr>
                <td class="td_style2"></td>                
            </tr>
        </table>

    </body>
</html>
