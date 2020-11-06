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
 * 删帖服务
 */
public class deleteTopic_process extends HttpServlet {

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
        }else{
            /*获取值*/
            String topicId = request.getParameter("topicId");
            /*开始删帖*/
            topic_dao topicDAO = new topic_dao();
            try{
                topicDAO.delTopicById(topicId);
            }catch(SQLException sqle){
            }
            
            out.println("帖子删除成功，3秒后页面将跳转");
            /*超级管理员和版主跳转到管理页面去玩耍*/
            if(user.getGroup()==1){
                response.setHeader("refresh", "3;URL =manage.jsp");
            }else{
                response.setHeader("refresh", "3;URL =index.jsp");
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
