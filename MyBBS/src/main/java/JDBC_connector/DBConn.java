/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBC_connector;

/**
 *
 * @author 15850
 * 封装连接数据库的一些操作
 */

import java.sql.*;
import java.util.*;

public class DBConn {
    private static Connection conn = null;
    
    public static Connection getCon()
    {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String user = "root";
            String pwd = "a1585093722*";
            String url = "jdbc:mysql://localhost:3306/bbs_db";
            conn = DriverManager.getConnection(url+"?useUnicode=true&characterEncoding=utf-8", user, pwd);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return conn;
    }
}
