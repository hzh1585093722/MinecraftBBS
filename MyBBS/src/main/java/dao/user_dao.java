/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import bean.users;
import JDBC_connector.DBConn;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author 15850 关于用户的增删改查
 */
public class user_dao {

    /*新建用户，参数为用户对象，将其值插入表中,注册成功或者失败返回状态码*/
    public int NewUser(users user) throws SQLException {
        Connection conn = DBConn.getCon();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "";
        /*状态字*/
        int success = 1;
        int emailExist = 0;
        int usernameExist = -1;

        sql = "select username from users where username = '" + user.getUsername() + "'";

        pstmt = null;
        rs = null;
        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery();

        if (rs.next()) {
            pstmt.close();
            rs.close();
            conn.close();

            return usernameExist;
        }

        sql = "select email from users where email = '" + user.getEmail() + "'";
        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery();

        if (rs.next()) {
            pstmt.close();
            rs.close();
            conn.close();

            return emailExist;
        }

        sql = "insert into users values(";
        sql = sql + "default,'" + user.getUsername() + "','" + user.getPassword() + "',";
        sql = sql + "'0','" + user.getEmail() + "','" + user.getPhone() + "',";
        sql = sql + "'" + user.getQQ() + "',default,default,";
        sql = sql + "default,now(),default,'0')";

        pstmt = conn.prepareStatement(sql);
        pstmt.executeUpdate();

        rs.close();
        pstmt.close();
        conn.close();

        return success;
    }

    /*更新用户个人信息：qq,电话，生日，性别参数为用户对象*/
    public void UpdateUserInfo(users user) throws SQLException {
        Connection conn = DBConn.getCon();
        PreparedStatement pstmt = null;
        String sql = "";

        sql = "update users set `qq` = '" + user.getQQ() + "',";
        sql = sql + "`phone` = '" + user.getPhone() + "',";
        sql = sql + "`birth` = '" + user.getBirth() + "',";
        sql = sql + "`sex` = '" + user.getSex() + "'";
        sql = sql + "where `Id` = '" + user.getId() + "'";

        pstmt = conn.prepareStatement(sql);
        pstmt.executeUpdate();

        pstmt.close();
        conn.close();
    }

    /*更新用户头像,参数为用户对象*/
    public void UpdateUserAvatar(users user) throws SQLException {
        Connection conn = DBConn.getCon();
        PreparedStatement pstmt = null;
        String sql = "";

        sql = "update users set `avatar` = '" + user.getAvatar() + "' where ";
        sql = sql + "`Id` = '" + user.getId() + "'";

        pstmt = conn.prepareCall(sql);
        pstmt.executeUpdate();

        pstmt.close();
        conn.close();

    }

    /*封禁或者解封用户*/
    public void BanOrUnbanUser(users user) throws SQLException {
        Connection conn = DBConn.getCon();
        PreparedStatement pstmt = null;
        String sql = "";

        sql = "update users set `is_banned` = '" + user.getIs_banned() + "' where ";
        sql = sql + "`Id` = '" + user.getId() + "'";

        pstmt = conn.prepareStatement(sql);
        pstmt.executeUpdate();

        pstmt.close();
        conn.close();
    }

    /*根据用户id查看某个的用户个人信息,参数和返回值都为用户对象*/
    public users ViewUserInfo(users user) throws SQLException {
        Connection conn = DBConn.getCon();
        PreparedStatement pstmt = null;
        String sql = "";
        ResultSet rs = null;

        /*密码就不要选了*/
        sql = "select users.Id,`username`,`group`,`email`,`phone`,`qq`,`sex`,`birth`,";
        sql = sql + "`avatar`,`create_time`,`update_time`,`is_banned`,manager.job ";
        sql = sql + "from users  left join manager on users.id = manager.uid ";
        sql = sql + "where users.id = "+user.getId()+"";

        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery();

        /*读取查询到的纪录，如果第一条纪录不为空*/
        if (rs.next()) {
            user.setId(rs.getInt("Id"));
            user.setUsername(rs.getString("username"));
            user.setGroup(rs.getInt("group"));
            user.setEmail(rs.getString("email"));
            user.setPhone(rs.getString("phone"));
            user.setQQ(rs.getString("qq"));
            user.setSex(rs.getInt("sex"));
            user.setBirth(rs.getString("birth"));
            user.setAvatar(rs.getString("avatar"));
            user.setCreate_time(rs.getString("create_time"));
            user.setUpdate_time(rs.getString("update_time"));
            user.setIs_banned(rs.getInt("is_banned"));
            user.setJob(rs.getInt("job"));
        }

        rs.close();
        pstmt.close();
        conn.close();

        return user;
    }

    /*根据用户输入的字符串来寻找用户，返回用户对象的结果集*/
    public ArrayList SearchUserByName(String user_input) throws SQLException {
        ArrayList user_list = new ArrayList();
        Connection conn = DBConn.getCon();
        PreparedStatement pstmt = null;
        String sql = "";
        ResultSet rs = null;

        /*需要用到外联查询了，无论manager表有没有用户的信息，都要获得用户纪录*/
 /*下面是以users表为主并获取manager表job字段的左外联查询*/
        sql = "select users.Id,`username`,`group`,`email`,`phone`,`qq`,`sex`,`birth`,";
        sql = sql + "`avatar`,`create_time`,`update_time`,`is_banned`,manager.job from users ";
        sql = sql + "left join manager on users.id = manager.uid ";
        sql = sql + "where `username` like '%" + user_input + "%'";

        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery();

        /*读取查询到的纪录，如果第一条纪录不为空*/
        while (rs.next()) {
            users user = new users();

            user.setId(rs.getInt("Id"));
            user.setUsername(rs.getString("username"));
            user.setGroup(rs.getInt("group"));
            user.setEmail(rs.getString("email"));
            user.setPhone(rs.getString("phone"));
            user.setQQ(rs.getString("qq"));
            user.setSex(rs.getInt("sex"));
            user.setBirth(rs.getString("birth"));
            user.setAvatar(rs.getString("avatar"));
            user.setCreate_time(rs.getString("create_time"));
            user.setUpdate_time(rs.getString("update_time"));
            user.setIs_banned(rs.getInt("is_banned"));
            user.setJob(rs.getInt("manager.job"));

            user_list.add(user);
        }

        rs.close();
        pstmt.close();
        conn.close();

        return user_list;
    }

    /*查看被封禁的用户，返回被封禁的用户列表*/
    public ArrayList ViewBannedUser() throws SQLException {
        ArrayList user_list = new ArrayList();
        Connection conn = DBConn.getCon();
        PreparedStatement pstmt = null;
        String sql = "";
        ResultSet rs = null;

        sql = "select users.Id,`username`,`group`,`email`,`phone`,`qq`,`sex`,`birth`,";
        sql = sql + "`avatar`,`create_time`,`update_time`,`is_banned`,manager.job from users ";
        sql = sql + "left join manager on users.id = manager.uid ";
        sql = sql + "where `is_banned` = 1";

        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery();

        /*读取查询到的纪录，如果第一条纪录不为空*/
        while (rs.next()) {
            users user = new users();

            user.setId(rs.getInt("Id"));
            user.setUsername(rs.getString("username"));
            user.setGroup(rs.getInt("group"));
            user.setEmail(rs.getString("email"));
            user.setPhone(rs.getString("phone"));
            user.setQQ(rs.getString("qq"));
            user.setSex(rs.getInt("sex"));
            user.setBirth(rs.getString("birth"));
            user.setAvatar(rs.getString("avatar"));
            user.setCreate_time(rs.getString("create_time"));
            user.setUpdate_time(rs.getString("update_time"));
            user.setIs_banned(rs.getInt("is_banned"));
            user.setJob(rs.getInt("manager.job"));
            
            user_list.add(user);
        }

        rs.close();
        pstmt.close();
        conn.close();

        return user_list;
    }

    /*查看管理组人员*/
    public ArrayList ViewManager() throws SQLException {
        ArrayList user_list = new ArrayList();
        Connection conn = DBConn.getCon();
        PreparedStatement pstmt = null;
        String sql = "";
        ResultSet rs = null;

        sql = "select users.Id,`username`,`group`,`email`,`phone`,`qq`,`sex`,`birth`,";
        sql = sql + "`avatar`,`create_time`,`update_time`,`is_banned`,manager.job from users ";
        sql = sql + "left join manager on users.id = manager.uid ";
        sql = sql + "where `group` = 1";
        
        
        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery();

        /*读取查询到的纪录，如果第一条纪录不为空*/
        while (rs.next()) {
            users user = new users();

            user.setId(rs.getInt("Id"));
            user.setUsername(rs.getString("username"));
            user.setGroup(rs.getInt("group"));
            user.setEmail(rs.getString("email"));
            user.setPhone(rs.getString("phone"));
            user.setQQ(rs.getString("qq"));
            user.setSex(rs.getInt("sex"));
            user.setBirth(rs.getString("birth"));
            user.setAvatar(rs.getString("avatar"));
            user.setCreate_time(rs.getString("create_time"));
            user.setUpdate_time(rs.getString("update_time"));
            user.setIs_banned(rs.getInt("is_banned"));
            user.setJob(rs.getInt("manager.job"));

            user_list.add(user);
        }
        
        rs.close();
        pstmt.close();
        conn.close();

        return user_list;
    }

    public users GetUserInfoByEmail(String email) throws SQLException {
        users user = new users();

        Connection conn = DBConn.getCon();
        PreparedStatement pstmt = null;
        String sql = "";
        ResultSet rs = null;

        sql = "select users.Id,`username`,`group`,`email`,`phone`,`qq`,`sex`,`birth`,";
        sql = sql + "`avatar`,`create_time`,`update_time`,`is_banned`,manager.job from users ";
        sql = sql + "left join manager on users.id = manager.uid ";
        sql = sql + "where `email` = '" + email + "'";

        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery();

        /*读取查询到的纪录，如果第一条纪录不为空*/
        if (rs.next()) {
            user.setId(rs.getInt("Id"));
            user.setUsername(rs.getString("username"));
            user.setGroup(rs.getInt("group"));
            user.setEmail(rs.getString("email"));
            user.setPhone(rs.getString("phone"));
            user.setQQ(rs.getString("qq"));
            user.setSex(rs.getInt("sex"));
            user.setBirth(rs.getString("birth"));
            user.setAvatar(rs.getString("avatar"));
            user.setCreate_time(rs.getString("create_time"));
            user.setUpdate_time(rs.getString("update_time"));
            user.setIs_banned(rs.getInt("is_banned"));
            user.setJob(rs.getInt("manager.job"));
        }

        rs.close();
        pstmt.close();
        conn.close();

        return user;

    }

    
    /*更新用户的权限组*/
    public void UpdateGroup(users user) throws SQLException{
        Connection conn = DBConn.getCon();
        PreparedStatement pstmt = null;
        String sql = "";
        
        sql = "update users set `group`="+user.getGroup()+" ";
        sql = sql + "where `Id` = "+user.getId()+"";
        
        pstmt = conn.prepareStatement(sql);
        pstmt.executeUpdate();
        
        pstmt.close();
        conn.close();
    }
}
