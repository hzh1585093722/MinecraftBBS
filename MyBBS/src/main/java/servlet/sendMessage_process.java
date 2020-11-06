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
 * @author 15850 发送消息
 */
public class sendMessage_process extends HttpServlet {

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

        /*获取用户信息*/
        users user = (users) request.getSession().getAttribute("users");

        if (user == null) {
            out.println("无法获取您的用户信息，请登录。3秒后页面将跳转");
            response.setHeader("refresh", "3;URL =index.jsp");
        } else {

            /*获取当前登录的用户ID*/
            int uid = user.getId();
            /*接收表单传来的值*/
            String userEmail = request.getParameter("userEmail");
            String content = request.getParameter("content");
            /*获取目标用户的用户id*/
            user_dao userDAO = new user_dao();
            users target_user = new users();

            try {
                target_user = userDAO.GetUserInfoByEmail(userEmail);
            } catch (SQLException sqle) {
            }

            /*如果查得用户得邮箱为空*/
            if (target_user.getEmail() == null) {
                out.println("目标邮箱不存在！3秒后页面将跳转");
                response.setHeader("refresh", "3;URL =message.jsp");
            } else {

                /*新建消息，并将其储存进数据库中*/
                message new_message = new message();
                new_message.setContent(content);
                new_message.setSender_id(uid);
                new_message.setReceiver_id(target_user.getId());
                /*数据库中消息类型为3表示私人消息*/
                new_message.setType(3);

                message_dao messageDAO = new message_dao();

                try {
                    messageDAO.NewMessage(new_message);
                } catch (SQLException sqle) {
                }

                out.println("消息发送成功！3秒后页面将跳转");
                response.setHeader("refresh", "3;URL =message.jsp");
            }

        }

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
