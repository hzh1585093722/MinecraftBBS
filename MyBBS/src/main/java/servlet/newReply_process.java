/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import JDBC_connector.DBConn;
import dao.*;
import bean.*;
import java.sql.SQLException;

/**
 *
 * @author 15850
 */
public class newReply_process extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        /*获取表单值*/
        String topicId = request.getParameter("topicId");
        String content = request.getParameter("content");
        /*获取用户信息*/
        users user = (users) request.getSession().getAttribute("users");

        if (user == null) {
            out.println("无法获取您的用户信息，请登录。3秒后页面将跳转");
            response.setHeader("refresh", "3;URL =index.jsp");
        }
        /*获取当前登录的用户ID*/
        int uid = user.getId();
        /*创建新的回复*/
        reply new_reply = new reply();
        new_reply.setReply_uid(uid);
        new_reply.setContent(content);
        new_reply.setTopic_id(topicId);

        reply_dao replyDAO = new reply_dao();

        try {
            replyDAO.NewReply(new_reply);
        } catch (SQLException sqle) {

        }

        /*自动发送消息告诉楼主有人回帖,但是如果回帖的人是楼主本人，就不要发送消息*/
 /*获取帖子信息*/
        topic this_topic = new topic();
        topic_dao topicDAO = new topic_dao();

        try {
            this_topic = topicDAO.getTopicInfoById(topicId);
        } catch (SQLException sqle) {
        }
        /*判断回复的人是不是楼主本人*/
        if (user.getId() != this_topic.getUser_id()) {
            /*消息类型*/
            int m_reply = 1;/*回复通知*/
 /*消息发送者:系统*/
            int m_sender = 0;
            /*消息内容*/
            String message_content = "用户<a href=\"userInfo.jsp?uid=" + user.getId() + "\">";
            message_content = message_content + "" + user.getUsername() + "(uid:" + user.getId() + ")";
            message_content = message_content + "</a>评论了你的帖子:<br/>";
            message_content = message_content + "标题：<a href=\"topic.jsp?topicId=" + this_topic.getId() + "\">";
            message_content = message_content + "" + this_topic.getTitle() + "</a>";
            /*新建消息*/
            message new_message = new message();
            new_message.setContent(message_content);
            new_message.setType(m_reply);
            new_message.setSender_id(m_sender);
            new_message.setReceiver_id(this_topic.getUser_id());/*收件人为楼主*/

            message_dao messageDAO = new message_dao();

            try {
                messageDAO.NewMessage(new_message);
            } catch (SQLException sqle) {

            }
        }

        out.println("回复发送成功，3秒后页面将跳转");
        response.setHeader("refresh", "3;URL =topic.jsp?topicId=" + topicId);

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
