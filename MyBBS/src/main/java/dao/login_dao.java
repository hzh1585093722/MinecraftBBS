/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import bean.users;
import JDBC_connector.DBConn;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author 15850 验证登录信息
 */
public class login_dao {

    /*通过邮箱和密码登录，如果继承该类，可以重写此方法*/
    public static boolean isTrue(users user) throws SQLException {
        Connection conn = DBConn.getCon();
        String sql = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        sql = "select `Id` from users where ";
        sql = sql + "`email` = '" + user.getEmail() + "' and `password` = ";
        sql = sql + "'" + user.getPassword() + "'";
        
        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery();        

        if (rs.next()) {
            conn = DBConn.getCon();
            pstmt = null;
            
            /*更新用户的登录时间*/
            sql = "update users set `update_time` = now() where ";
            sql = sql + "`email` = '" + user.getEmail() + "'";

            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();

            rs.close();

            return true;
        }

        rs.close();
        pstmt.close();
        conn.close();

        return false;
    }
}
