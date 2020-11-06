<%-- 
    Document   : manage
    Created on : 2020-11-2, 23:47:35
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
        <title>论坛管理界面</title>
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

        <!--检查用户的权限-->
        <%
            if (user == null) {
                out.print("<br/>请先登录");
                response.setHeader("refresh", "2;login.jsp");

            } else if (user.getGroup() == 0) {
                out.print("<br/>你没有权限访问这个页面");
                response.setHeader("refresh", "2;index.jsp");
            } else {
                /*管理员尽情当权限狗的地方*/
        %>
        <table>
            <tr>
                <td colspan="2">论坛管理窗口</td>
            </tr>
            <tr>
                <td class="td_style">
                    <a href="manage.jsp">更改用户权限</a>                    
                </td>
                <td rowspan="6"  ALIGN="left" VALIGN="top">
                    <!--控制功能输出-->
                    <%
                        String menu = "";
                        menu = request.getParameter("menu");
                        if (menu == null) {
                            /*更改用户权限*/
 /*只有超级管理员才能更改权限*/
                            if (user.getJob() != 0) {
                                out.print("只有超级管理员才可以更改用户权限");
                            } else if (user.getJob() == 0) {
                    %>
                    <br/>
                    <form action="manage.jsp">
                        搜索用户:<input type="search" name="userInput"/><br/><br/>
                        <input type="submit" value="搜索"/>
                    </form>                            
                    <%
                        String userInput = request.getParameter("userInput");
                        if (userInput == null || userInput.equals("")) {
                            /*不做操作*/
                        } else {
                            user_dao userDAO = new user_dao();
                            ArrayList userlist = new ArrayList();
                            userlist = userDAO.SearchUserByName(userInput);
                            if (userlist.isEmpty()) {
                                out.print("未找到纪录");
                            } else {
                                out.print("找到" + userlist.size() + "条纪录<br/>");
                                users one_user = new users();
                                for (int i = 0; i < userlist.size(); i++) {
                                    one_user = (users) userlist.get(i);
                    %>
                    <hr/>
                    <form action="updateUserGroup">
                        <input type="hidden" name="userId" value="<%=one_user.getId()%>"/>
                        用户id:<%=one_user.getId()%><br/>
                        用户邮箱:<%=one_user.getEmail()%><br/>
                        用户名:<%=one_user.getUsername()%><br/>
                        当前身份:
                        <%
                            if (one_user.getGroup() == 0) {
                                out.print("普通用户");
                            } else {
                                switch (one_user.getJob()) {
                                    case 0:
                                        out.print("超级管理员");
                                        break;
                                    case 2:
                                        out.print("问答区版主");
                                        break;
                                    case 3:
                                        out.print("MOD区版主");
                                        break;
                                    case 4:
                                        out.print("插件区版主");
                                        break;
                                    case 5:
                                        out.print("服务器区版主");
                                        break;
                                }
                            }
                        %><br/>
                        请选择要更改的身份:
                        <select name="job">
                            <option value="-1">普通用户</option>
                            <option value="0">超级管理员</option>
                            <option value="2">问答区版主</option>
                            <option value="3">MOD区版主</option>
                            <option value="4">插件区版主</option>
                            <option value="5">服务器区版主</option>
                        </select><br/><br/>
                        <input type="submit" value="提交更改"/>
                    </form><hr/>
                    <%
                                    }
                                }
                            }
                        }

                    } else if (menu.equals("")) {
                        response.sendRedirect("manage.jsp");
                    } else if (menu.equals("checkmanager")) {
                        user_dao userDAO = new user_dao();
                        ArrayList userlist = new ArrayList();
                        userlist = userDAO.ViewManager();
                        if (userlist.isEmpty()) {
                            out.print("未找到纪录");
                        } else {
                            out.print("找到" + userlist.size() + "条纪录<br/>");
                            users one_user = new users();
                            for (int i = 0; i < userlist.size(); i++) {
                                one_user = (users) userlist.get(i);
                    %>
                    <hr/>
                    <form action="updateUserGroup">
                        <input type="hidden" name="userId" value="<%=one_user.getId()%>"/>
                        用户id:<%=one_user.getId()%><br/>
                        用户邮箱:<%=one_user.getEmail()%><br/>
                        用户名:<%=one_user.getUsername()%><br/>
                        当前身份:
                        <%
                            if (one_user.getGroup() == 0) {
                                out.print("普通用户");
                            } else {
                                switch (one_user.getJob()) {
                                    case 0:
                                        out.print("超级管理员");
                                        break;
                                    case 2:
                                        out.print("问答区版主");
                                        break;
                                    case 3:
                                        out.print("MOD区版主");
                                        break;
                                    case 4:
                                        out.print("插件区版主");
                                        break;
                                    case 5:
                                        out.print("服务器区版主");
                                        break;
                                }
                            }
                            /*只有超级管理员才能更改用户权限*/
                            if (user.getJob() != 0) {
                                out.print("<br/>只有超级管理员才可以更改用户权限");
                            } else if (user.getJob() == 0) {
                        %><br/>
                        请选择要更改的身份:
                        <select name="job">
                            <option value="-1">普通用户</option>
                            <option value="0">超级管理员</option>
                            <option value="2">问答区版主</option>
                            <option value="3">MOD区版主</option>
                            <option value="4">插件区版主</option>
                            <option value="5">服务器区版主</option>
                        </select><br/><br/>
                        <input type="submit" value="提交更改"/>
                    </form><hr/>
                    <%
                                }

                            }
                        }
                    } else if (menu.equals("BanUser")) {
                        /*只有超级管理员才能封号解封*/
                        if (user.getJob() != 0) {
                            out.print("只有超级管理员才可以封禁和解封用户");
                        } else if (user.getJob() == 0) {
                    %>
                    <br/>
                    <form action="manage.jsp">
                        <input type="hidden" name="menu" value="BanUser"/>                          
                        搜索用户:<input type="search" name="userInput"/><br/><br/>
                        <input type="submit" value="搜索"/>
                    </form>                            
                    <%
                        String userInput = request.getParameter("userInput");
                        if (userInput == null || userInput.equals("")) {
                            /*不做操作*/
                        } else {
                            user_dao userDAO = new user_dao();
                            ArrayList userlist = new ArrayList();
                            userlist = userDAO.SearchUserByName(userInput);
                            if (userlist.isEmpty()) {
                                out.print("未找到纪录");
                            } else {
                                out.print("找到" + userlist.size() + "条纪录<br/>");
                                users one_user = new users();
                                for (int i = 0; i < userlist.size(); i++) {
                                    one_user = (users) userlist.get(i);
                    %>
                    <hr/>
                    <form action="BanUser">
                        <input type="hidden" name="userId" value="<%=one_user.getId()%>"/>
                        用户id:<%=one_user.getId()%><br/>
                        用户邮箱:<%=one_user.getEmail()%><br/>
                        用户名:<%=one_user.getUsername()%><br/>
                        当前身份:
                        <%
                            if (one_user.getGroup() == 0) {
                                out.print("普通用户");
                            } else {
                                switch (one_user.getJob()) {
                                    case 0:
                                        out.print("超级管理员");
                                        break;
                                    case 2:
                                        out.print("问答区版主");
                                        break;
                                    case 3:
                                        out.print("MOD区版主");
                                        break;
                                    case 4:
                                        out.print("插件区版主");
                                        break;
                                    case 5:
                                        out.print("服务器区版主");
                                        break;
                                }
                            }
                        %><br/>

                        <input type="submit" value="封禁"/>
                    </form><hr/>
                    <%
                                    }
                                }
                            }
                        }

                    } else if (menu.equals("UnBanUser")) {
                        /*解封界面*/
 /*只有超级管理员才能封号解封*/
                        if (user.getJob() != 0) {
                            out.print("只有超级管理员才可以封禁和解封用户");
                        } else if (user.getJob() == 0) {
                            user_dao userDAO = new user_dao();
                            ArrayList userlist = new ArrayList();
                            userlist = userDAO.ViewBannedUser();
                            if (userlist.isEmpty()) {
                                out.print("未找到纪录");
                            } else {
                                out.print("找到" + userlist.size() + "条纪录<br/>");
                                users one_user = new users();
                                for (int i = 0; i < userlist.size(); i++) {
                                    one_user = (users) userlist.get(i);
                    %>
                    <hr/>
                    <form action="UnbanUser">
                        <input type="hidden" name="userId" value="<%=one_user.getId()%>"/>
                        用户id:<%=one_user.getId()%><br/>
                        用户邮箱:<%=one_user.getEmail()%><br/>
                        用户名:<%=one_user.getUsername()%><br/>
                        当前身份:
                        <%
                            if (one_user.getGroup() == 0) {
                                out.print("普通用户");
                            } else {
                                switch (one_user.getJob()) {
                                    case 0:
                                        out.print("超级管理员");
                                        break;
                                    case 2:
                                        out.print("问答区版主");
                                        break;
                                    case 3:
                                        out.print("MOD区版主");
                                        break;
                                    case 4:
                                        out.print("插件区版主");
                                        break;
                                    case 5:
                                        out.print("服务器区版主");
                                        break;
                                }
                            }
                        %><br/>

                        <input type="submit" value="解封"/>
                    </form><hr/>
                    <%
                                }
                            }
                        }
                    } else if (menu.equals("ManageTopic")) {
                        /*管理帖子*/
                        switch (user.getJob()) {
                            case 0:
                                out.print("您是超级管理员，可以查看所有帖子<br/>");
                                break;
                            case 2:
                                out.print("您是问答区版主，只能查看该板块帖子<br/>");
                                break;
                            case 3:
                                out.print("您是MOD区版主，只能查看该板块帖子<br/>");
                                break;
                            case 4:
                                out.print("您是插件区版主，只能查看该板块帖子<br/>");
                                break;
                            case 5:
                                out.print("您是服务器区区版主，只能查看该板块帖子<br/>");
                                break;
                        }
                        /*如果是超级管理员,获取所有帖子*/
                        if (user.getJob() == 0) {
                            ArrayList topicList = new ArrayList();
                            topic_dao topicDAO = new topic_dao();
                            topicList = topicDAO.getTopicList();

                            /*输出结果*/
                            if (topicList.isEmpty()) {
                                out.print("没有找到帖子信息<br/>");
                            } else {
                                for (int i = 0; i < topicList.size(); i++) {
                                    topic one_topic = new topic();
                                    one_topic = (topic) topicList.get(i);
                    %>
                    <hr/>
                    <form action="deleteTopic_process">                        
                        <input type="hidden" name="topicId" value="<%=one_topic.getId()%>"/><br/>
                        帖子标题：<a href="topic.jsp?topicId=<%=one_topic.getId()%>">
                            <%=one_topic.getTitle()%>
                        </a><br/>
                        发帖人：<a href="userInfo.jsp?uid=<%=one_topic.getUser_id()%>">
                            <%=one_topic.getUsername()%>
                        </a><br/>
                        发帖时间:<%=one_topic.getCreate_time()%><br/>
                        <input type="submit" value="删除"/>
                    </form>
                    <hr/>
                    <%
                            }
                        }
                    } else {
                        /*如果是版主，获取管理板块的帖子*/
                        ArrayList topicList = new ArrayList();
                        topic_dao topicDAO = new topic_dao();
                        topicList = topicDAO.getTopicListById(user.getJob());

                        /*输出结果*/
                        if (topicList.isEmpty()) {
                            out.print("没有找到帖子信息<br/>");
                        } else {
                            for (int i = 0; i < topicList.size(); i++) {
                                topic one_topic = new topic();
                                one_topic = (topic) topicList.get(i);
                    %>
                    <hr/>
                    <form action="deleteTopic_process">                        
                        <input type="hidden" name="topicId" value="<%=one_topic.getId()%>"/><br/>
                        帖子标题：<a href="topic.jsp?topicId=<%=one_topic.getId()%>">
                            <%=one_topic.getTitle()%>
                        </a><br/>
                        发帖人：<a href="userInfo.jsp?uid=<%=one_topic.getUser_id()%>">
                            <%=one_topic.getUsername()%>
                        </a><br/>
                        发帖时间:<%=one_topic.getCreate_time()%><br/>
                        <input type="submit" value="删除"/>
                    </form>
                    <hr/>
                    <%
                                    }
                                }
                            }

                        }
                    %>
                </td>
            </tr>
            <tr class="td_style">
                <td>
                    <a href="manage.jsp?menu=checkmanager">查看管理组成员</a>                                     
                </td>
            </tr>
            <tr class="td_style">
                <td>
                    <a href="manage.jsp?menu=BanUser">封禁用户</a>                                     
                </td>
            </tr>
            <tr class="td_style">
                <td>
                    <a href ="manage.jsp?menu=UnBanUser">解封用户</a>                    
                </td>
            </tr>
            <tr class="td_style">
                <td>
                    <a href="manage.jsp?menu=ManageTopic">管理帖子</a>                    
                </td>
            </tr>
            <!--留空-->
            <tr>
                <td class="td_style2"></td>
            </tr>
        </table><br/><br/>
        <%
            }
        %>


    </body>
</html>
