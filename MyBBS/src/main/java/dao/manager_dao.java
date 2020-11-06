/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import bean.*;
import JDBC_connector.DBConn;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author 15850 管理组表
 */
public class manager_dao {

    /*更新用户的管理岗位，如果不在则插入*/
    public void updateManager(users user) throws SQLException {
        Connection conn = DBConn.getCon();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "";
        /*先看manager表有没有用户的id*/
        sql = "select uid from manager where uid =" + user.getId() + "";

        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery();

        /*如果manager表找得到用户的id（用户已经担任管理职位），就更新，没有则创建*/
        if (rs.next()) {
            sql = "update manager set job = " + user.getJob() + " ";
            sql = sql + "where uid = " + user.getId() + "";

            pstmt = null;
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        } else {
            sql = "insert into manager values(default," + user.getId() + "," + user.getJob() + ")";
            pstmt = null;
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        }

        rs.close();
        pstmt.close();
        conn.close();
    }

    /*如果把用户降级为普通用户，则需要在manager表中删除掉和用户有关的信息*/
    public void DeleteUser(users user) throws SQLException {
        Connection conn = DBConn.getCon();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "";

        /*先检查表中有没有该用户的信息,有则删除，无则不用*/
        sql = "select uid from manager where uid = " + user.getId() + "";
        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery();

        if (rs.next()) {
            sql = "delete from manager where uid=" + user.getId() + "";
            
            pstmt = null;
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        }

        rs.close();
        pstmt.close();
        conn.close();
    }
}
