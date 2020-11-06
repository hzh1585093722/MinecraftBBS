/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.PreparedStatement;

import bean.tab;
import JDBC_connector.DBConn;
import java.sql.SQLException;

/**
 *
 * @author 15850 板块的数据库访问对象
 */
public class tab_dao {

    public tab GetTabInfoByName(String tab_name_en) throws SQLException {
        tab one_tab = new tab();
        Connection conn = DBConn.getCon();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "";
        
        sql = "select `Id`,`tab_name`,`tab_name_en` from tab where ";
        sql = sql + "`tab_name_en` = '"+tab_name_en+"'";
        
        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery();
        
        if(rs.next()){
            one_tab.setId(rs.getInt("Id"));
            one_tab.setTab_name(rs.getString("tab_name"));
            one_tab.setTab_name_en(rs.getString("tab_name_en"));        
        }
        
        rs.close();
        pstmt.close();
        conn.close();
        
        return one_tab;
    }
}
