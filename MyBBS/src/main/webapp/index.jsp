<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bean.*" %>
<%@page import="dao.*" %>
<%@page import="JDBC_connector.DBConn" %>
<%@page import="java.util.ArrayList" %>

<html>
    <head>
        <title>论坛首页</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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

        <!--获取帖子的概要数据-->
        <%
            ArrayList noticeList = new ArrayList();
            ArrayList FAQsList = new ArrayList();
            ArrayList MODList = new ArrayList();
            ArrayList pluginList = new ArrayList();
            ArrayList serverList = new ArrayList();

            topic one_topic = null;

            topic_dao topicDAO = new topic_dao();
            noticeList = topicDAO.getSomeNewestTopicList("10", "notice");
            FAQsList = topicDAO.getSomeNewestTopicList("10", "FAQs");
            MODList = topicDAO.getSomeNewestTopicList("10", "MOD");
            pluginList = topicDAO.getSomeNewestTopicList("10", "plugin");
            serverList = topicDAO.getSomeNewestTopicList("10", "server");
        %>

        <!--页面中部-->
        <a href="tab.jsp?tabname=notice">公告区</a><br/><br/>
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
                if (noticeList.isEmpty()) {
            %>
            <td>该版块暂未有人发帖</td>
            <%
            } else {
                for (int i = 0; i < noticeList.size(); i++) {
                    one_topic = (topic) noticeList.get(i);
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
            <td>
                <a href="tab.jsp?tabname=notice">查看更多</a>
            </td>
            <%
                }
            %>
        </table><br/><br/>

        <a href="tab.jsp?tabname=FAQs">问答区</a><br/><br/>
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
                if (FAQsList.isEmpty()) {
            %>
            <td>该版块暂未有人发帖</td>
            <%
            } else {
                for (int i = 0; i < FAQsList.size(); i++) {
                    one_topic = (topic) FAQsList.get(i);
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
            <td>
                <a href="tab.jsp?tabname=FAQs">查看更多</a>
            </td>
            <%
                }
            %>
        </table><br/><br/>

        <a href="tab.jsp?tabname=MOD">MOD区</a><br/><br/>
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
                if (MODList.isEmpty()) {
            %>
            <td>该版块暂未有人发帖</td>
            <%
            } else {
                for (int i = 0; i < MODList.size(); i++) {
                    one_topic = (topic) MODList.get(i);
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
            <td>
                <a href="tab.jsp?tabname=MOD">查看更多</a>
            </td>
            <%
                }
            %>
        </table><br/><br/>

        <a href="tab.jsp?tabname=plugin">插件区</a><br/><br/>
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
                if (pluginList.isEmpty()) {
            %>
            <td>该版块暂未有人发帖</td>
            <%
            } else {
                for (int i = 0; i < pluginList.size(); i++) {
                    one_topic = (topic) pluginList.get(i);
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
            <td>
                <a href="tab.jsp?tabname=plugin">查看更多</a>
            </td>
            <%
                }
            %>
        </table><br/><br/>

        <a href="tab.jsp?tabname=server">服务器区</a><br/><br/>
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
                if (serverList.isEmpty()) {
            %>
            <td>该版块暂未有人发帖</td>
            <%
            } else {
                for (int i = 0; i < serverList.size(); i++) {
                    one_topic = (topic) serverList.get(i);
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
            <td>
                <a href="tab.jsp?tabname=server">查看更多</a>
            </td>
            <%
                }
            %>
        </table><br/><br/>

    </body>
</html>
