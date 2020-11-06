<%-- 
    Document   : tab
    Created on : 2020-11-2, 23:47:10
    Author     : 15850
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bean.*" %>
<%@page import="JDBC_connector.DBConn" %>
<%@page import="dao.*" %>
<%@page import="java.util.ArrayList" %>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>板块页面</title>
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
            /*获取权限0为普通，1管理组*/
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

        <!--输出板块的帖子信息-->
        <%
            /*接受URL中的值,获取版块名*/
            String tab_name = request.getParameter("tabname");
            /*获取该版块的信息*/
            tab one_tab = new tab();
            tab_dao tabDAO = new tab_dao();
            one_tab = tabDAO.GetTabInfoByName(tab_name);

            /*获取板块帖子信息*/
            if (one_tab.getTab_name() == null) {
                out.println("无法获取板块信息");
            } else {
                ArrayList topic_list = new ArrayList();
                topic_dao topicDAO = new topic_dao();
                topic_list = topicDAO.getTopicListByTabName(tab_name);

                if (topic_list.isEmpty()) {
                    out.println("该板块暂无人发帖");
                } else {
        %>
        <table style="width: 960px;border: black solid 1px">
            <tr>
                <td width:"600px">标题</td>
                <td width:"80px">发帖人</td>
                <td width:"100px">创建时间</td>
                <td width:"100px">更新时间</td>
                <td width:"40px">回复数</td>
                <td width:"40px">点击数</td>
            </tr>
            <%
                for (int i = 0; i < topic_list.size(); i++) {
                    topic one_topic = new topic();
                    one_topic = (topic) topic_list.get(i);
            %>
            <tr>
                <td>
                    <a href="topic.jsp?topicId=<%=one_topic.getId()%>">
                        <%=one_topic.getTitle()%>
                    </a>                            
                </td>
                <td>
                    <a href="userInfo.jsp?uid=<%=one_topic.getUser_id()%>">
                        <%=one_topic.getUsername()%>
                    </a>
                </td>
                <td><%=one_topic.getCreate_time()%></td>
                <td><%=one_topic.getUpdate_time()%></td>
                <td><%=one_topic.getReply_num()%></td>
                <td><%=one_topic.getClicks()%></td>
            </tr>
            <%
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
            if (one_tab.getTab_name() != null) {
                if (user == null) {
                    out.println("登录之后才可发帖");
        %>
        <br/><br/><br/>
        <%
        } else if (tab_name.equals("notice") && user.getGroup() == 0) {
            out.println("只有管理组才可以在该版块评论");
        %>
        <br/><br/><br/>
        <%
        } else if (user.getIs_banned() == 1) {
            out.print("您的账号当前已被管理员封禁，无法发帖或者回帖,有疑问请联系管理员<br/><br/>");
        } else {
        %>


        <form action="newtopic_process">
            <!--获取帖子名字-->
            <input type="hidden" name="tabname" value="<%=tab_name%>"/>
            帖子标题：<input type="text" name="title"/><br/><br/>
            帖子内容：<textarea name="content" rows="5" cols="100" style="resize: none"></textarea><br/>
            <input type="submit" value="发布新帖子">
        </form><br/><br/>
        <%
                }
            }
        %>
    </body>
</html>
