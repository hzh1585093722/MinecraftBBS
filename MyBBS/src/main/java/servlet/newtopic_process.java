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
import bean.*;
import dao.*;
import java.sql.SQLException;

/**
 *
 * @author 15850
 */
public class newtopic_process extends HttpServlet {

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
        PrintWriter out = response.getWriter();//创建打印流

        /*获取session中的用户信息*/
        users user = new users();
        user = (users) request.getSession().getAttribute("users");
        /*判断用户是否登录*/
        if (user == null) {
            out.println("无法验证您的身份，请重新登陆，3秒后页面将跳转至登录界面");
            response.setHeader("refresh", "3;URL =login.jsp");
        }

        
        /*获取板块id*/
        tab one_tab = new tab();
        tab_dao tabDAO = new tab_dao();
        /*获取版块名*/
        String tabname = request.getParameter("tabname");
        
        if(tabname == null){
            out.println("无法获取板块信息！3秒后返回首页");
            response.setHeader("refresh", "3;URL =index.jsp");
        }
        
        try {
            one_tab = tabDAO.GetTabInfoByName(tabname);
        } catch (SQLException sqle) {

        }
        
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        
        /*创建新话题并将其添加进数据库表中*/
        topic newtopic = new topic();
        newtopic.setTitle(title);
        newtopic.setContent(content);
        newtopic.setUser_id(user.getId());
        newtopic.setTab_id(one_tab.getId());

        topic_dao topicDAO = new topic_dao();
        try{
            topicDAO.NewTopic(newtopic);
        }catch(SQLException sqle){
            
        }
        
        out.println("帖子发布成功！3秒后跳转到论坛页面");
        response.setHeader("refresh", "3;URL =tab.jsp?tabname="+tabname);
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
