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

import bean.users;
import dao.*;
import java.sql.SQLException;

/**
 *
 * @author 15850 处理更新用户权限有关的问题
 */
public class updateUserGroup extends HttpServlet {

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
        /*类型转换*/
        int uid = Integer.parseInt(request.getParameter("userId"));
        int job = Integer.parseInt(request.getParameter("job"));

        /*获取当前登录用户信息*/
        users user = (users) request.getSession().getAttribute("users");

        /*不允许操作当前登录用户*/
        if (user == null) {
            out.print("请先登录再进行操作");
            response.setHeader("refresh", "2;login.jsp");
        } else if (user.getId() == uid) {
            out.print("不允许更改当前登录用户的权限!");
            response.setHeader("refresh", "2;URL=manage.jsp");
        } else {
            /*如果降级为普通用户,则在manager表中删去该用户的信息，并且将用户在用户表中的权限组设为0*/
            if (job == -1) {
                /*创建要修改的用户对象*/
                users target_user = new users();
                target_user.setId(uid);
                target_user.setGroup(0);

                user_dao userDAO = new user_dao();
                manager_dao managerDAO = new manager_dao();

                try {
                    userDAO.UpdateGroup(target_user);
                    managerDAO.DeleteUser(target_user);
                } catch (SQLException sqle) {
                }
                out.println("权限已更改！2秒后页面跳转");
                response.setHeader("refresh", "2;URL =manage.jsp");

            } else {
                /*创建要修改的用户对象*/
                users target_user = new users();
                target_user.setId(uid);
                target_user.setJob(job);
                target_user.setGroup(1);
                
                user_dao userDAO = new user_dao();
                manager_dao managerDAO = new manager_dao();
                
                try {
                    userDAO.UpdateGroup(target_user);
                    managerDAO.updateManager(target_user);
                } catch (SQLException sqle) {
                }
                
                out.println("权限已更改！2秒后页面跳转");
                response.setHeader("refresh", "2;URL =manage.jsp");
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
