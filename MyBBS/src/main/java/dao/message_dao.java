/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import bean.message;
import java.util.ArrayList;
import java.sql.SQLException;
import JDBC_connector.DBConn;

/**
 *
 * @author 15850 数据库处理用户的消息
 */
public class message_dao {

    /*添加新消息,参数为消息对象*/
    public void NewMessage(message new_message) throws SQLException {
        Connection conn = DBConn.getCon();
        PreparedStatement pstmt = null;
        String sql = "";

        sql = "insert into message values(";
        sql = sql + "default,'" + new_message.getSender_id() + "',";
        sql = sql + "'" + new_message.getReceiver_id() + "',";
        sql = sql + "'" + new_message.getType() + "',";
        sql = sql + "'" + new_message.getContent() + "',now())";

        pstmt = conn.prepareStatement(sql);
        pstmt.executeUpdate();

        pstmt.close();
        conn.close();
    }

    /*用户获取个人消息和接受者为所有人的系统消息,参数为用户的id*/
    public ArrayList getMessage(int receiver_id) throws SQLException {
        ArrayList message_list = new ArrayList();
        Connection conn = DBConn.getCon();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "";

        sql = "select message.Id,sender_id,receiver_id,`type`,message.content,message.create_time ";
        sql = sql + "from message where receiver_id = " + receiver_id + " or receiver_id = 0 ";
        sql = sql + "order by message.create_time desc";

        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery();

        while (rs.next()) {
            message one_message = new message();

            one_message.setId(rs.getInt("Id"));
            one_message.setSender_id(rs.getInt("sender_id"));
            one_message.setReceiver_id(rs.getInt("receiver_id"));
            one_message.setType(rs.getInt("type"));
            one_message.setContent(rs.getString("content"));
            one_message.setCreate_time(rs.getString("message.create_time"));

            message_list.add(one_message);
        }

        rs.close();
        pstmt.close();
        conn.close();

        return message_list;
    }

    /*根据用户id和消息类型获取消息,0为系统消息，1为回复通知，2为点赞通知,3为私信*/
    public ArrayList getMessageListWithType(int receiver_id, int message_type) throws SQLException {
        ArrayList message_list = new ArrayList();
        Connection conn = DBConn.getCon();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "";

        /*如果是私人发的消息，还要获取一下用户名*/
        if (message_type == 3) {
            sql = "select message.Id,sender_id,receiver_id,`type`,message.content,message.create_time,users.username ";
            sql = sql + "from message,users where receiver_id = "+receiver_id+" and `type` = 3 and sender_id = users.Id ";
            sql = sql + "order by message.create_time desc";

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                message one_message = new message();

                one_message.setId(rs.getInt("Id"));
                one_message.setSender_id(rs.getInt("sender_id"));
                one_message.setReceiver_id(rs.getInt("receiver_id"));
                one_message.setType(rs.getInt("type"));
                one_message.setContent(rs.getString("content"));
                one_message.setCreate_time(rs.getString("message.create_time"));
                one_message.setUsername(rs.getString("users.username"));

                message_list.add(one_message);
            }
        } else {
            sql = "select message.Id,sender_id,receiver_id,`type`,message.content,message.create_time ";
            sql = sql + "from message where receiver_id = " + receiver_id + " and `type` = " + message_type + "";
            sql = sql + " order by message.create_time desc";

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                message one_message = new message();

                one_message.setId(rs.getInt("Id"));
                one_message.setSender_id(rs.getInt("sender_id"));
                one_message.setReceiver_id(rs.getInt("receiver_id"));
                one_message.setType(rs.getInt("type"));
                one_message.setContent(rs.getString("content"));
                one_message.setCreate_time(rs.getString("message.create_time"));

                message_list.add(one_message);
            }
        }

        rs.close();
        pstmt.close();
        conn.close();

        return message_list;
    }

    /*获取自己发送的消息*/
    public ArrayList getMyPostedMessage(int userId) throws SQLException {
        ArrayList message_list = new ArrayList();
        Connection conn = DBConn.getCon();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "";

        sql = "select message.Id,sender_id,receiver_id,`type`,message.content,message.create_time,users.username ";
        sql = sql + "from message,users where `type` = 3 and sender_id = "+userId+" and receiver_id = users.Id ";
        sql = sql + "order by message.create_time desc";

        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery();

        while (rs.next()) {
            message one_message = new message();

            one_message.setId(rs.getInt("Id"));
            one_message.setSender_id(rs.getInt("sender_id"));
            one_message.setReceiver_id(rs.getInt("receiver_id"));
            one_message.setType(rs.getInt("type"));
            one_message.setContent(rs.getString("content"));
            one_message.setCreate_time(rs.getString("message.create_time"));
            one_message.setTarget_username(rs.getString("users.username"));

            message_list.add(one_message);
        }

        rs.close();
        pstmt.close();
        conn.close();

        return message_list;
    }
}
