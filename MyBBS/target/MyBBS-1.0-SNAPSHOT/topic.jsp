<%-- 
    Document   : topic
    Created on : 2020-11-3, 15:11:54
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
        <title>帖子</title>
        <style>
            td{
                text-align:left;
                vertical-align:top;

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

        <!--获取帖子ID和信息-->
        <%
            /*接受URL中的值,获取帖子ID*/
            String topicid = request.getParameter("topicId");
            topic one_topic = new topic();

            if (topicid == null) {
                out.println("无法获取帖子信息");
            } else if (topicid.equals("")) {
                out.println("无法获取帖子信息");
            } else {
                /*获取该帖的信息*/
                topic_dao topicDAO = new topic_dao();
                one_topic = topicDAO.getTopicInfoById(topicid);
                /*获取帖子的回复*/
                ArrayList replyList = new ArrayList();
                reply_dao replyDAO = new reply_dao();
                replyList = replyDAO.getReplyListById(topicid);

                /*获取帖子信息*/
                if (one_topic.getTitle() == null) {
                    out.println("无法获取帖子信息");
                } else {

                    /*打印楼主区域*/
        %>
        <table style="width: 960px;border: black solid 1px;">
            <tr>
                <td colspan="2" style="border-bottom: black solid 1px;">标题：<%=one_topic.getTitle()%></td>
            </tr>
            <tr>
                <td style="width: 240px;height: 300px;border-right: black solid 1px;border-bottom: black solid 1px; ">
                    <a href="userInfo.jsp?uid=<%=one_topic.getUser_id()%>">
                        <%=one_topic.getUsername()%>(用户ID:<%=one_topic.getUser_id()%>) 
                    </a><br/>
                    <a href="userInfo.jsp?uid=<%=one_topic.getUser_id()%>">
                        <img src="<%=one_topic.getUserAvatar()%>" width="120px" height="150px"/><br/>
                    </a><br/>
                    楼主
                </td>
                <td style="width: 700px;height: 300px;border-bottom: black solid 1px; ">
                    发布时间<%=one_topic.getCreate_time()%><br/><br/>
                    <%=one_topic.getContent()%>
                </td>
            </tr>
            <%
                /*打印回复*/
                if (replyList.isEmpty()) {
            %>
            <tr>
                <td colspan="2" style="border-bottom: black solid 1px;">楼主还在苦苦等待回帖的人</td>
            </tr>
            <%
            } else {
                /*前三个回复分别显示为：沙发，板凳，地板，后面显示楼层*/
                for (int i = 0; i < replyList.size(); i++) {
                    reply one_reply = new reply();
                    one_reply = (reply) replyList.get(i);
            %>
            <tr>
                <td style="width: 240px;height: 300px;border-right: black solid 1px;border-bottom: black solid 1px; ">
                    <a href="userInfo.jsp?uid=<%=one_reply.getReply_uid()%>">
                        <%=one_reply.getUsername()%>(用户ID:<%=one_reply.getReply_uid()%>) 
                    </a><br/>
                    <a href="userInfo.jsp?uid=<%=one_reply.getReply_uid()%>">
                        <img src="<%=one_reply.getUserAvatar()%>" width="120px" height="150px"/><br/>
                    </a><br/>
                    <%
                        if (i == 0) {
                    %>
                    沙发
                    <%
                    } else if (i == 1) {
                    %>
                    板凳
                    <%
                    } else if (i == 2) {
                    %>
                    地板
                    <%
                    } else {
                    %>
                    <!--i从0开始遍历列表的，而且楼主是1楼-->
                    <%=i + 2%>楼
                    <%
                        }
                    %>
                </td>
                <td style="width: 700px;height: 300px;border-bottom: black solid 1px; ">
                    发布时间<%=one_reply.getCreate_time()%><br/><br/>
                    <%=one_reply.getContent()%>
                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>
        <%
                }
            }
        %>
        <br/><br/>


        <!--控制评论区显示-->
        <%
            if (one_topic.getTitle() != null) {
                if (user == null) {
                    out.println("登录之后才可发帖");
        %>
        <br/><br/><br/>
        <%
        } else if (user.getIs_banned() == 1) {
            out.print("您的账号当前已被管理员封禁，无法发帖或者回帖,有疑问请联系管理员<br/><br/>");
        } else {
        %>
        <form action="newReply_process">
            <!--获取帖子的id-->
            <input type="hidden" name="topicId" value="<%=topicid%>"/>
            回复内容：<textarea name="content" rows="5" cols="100" style="resize: none"></textarea><br/>
            <input type="submit" value="发布评论">
        </form><br/><br/>
        <%
                }
            }
        %>
    </body>
</html>
