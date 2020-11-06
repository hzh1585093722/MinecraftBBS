/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import JDBC_connector.DBConn;
import bean.reply;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author 15850 帖子的回帖的增删改查
 */
public class reply_dao {

    /*根据帖子的id获取回帖信息*/
    public ArrayList getReplyByTabId(String topic_id) throws SQLException {
        ArrayList reply_list = new ArrayList();
        Connection conn = DBConn.getCon();
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        String sql = "";

        sql = "select `Id`,`topic_id`,`reply_uid`,`content`,`create_time` from reply ";
        sql = sql + "where topic_id = '" + topic_id + "'";

        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery();

        while (rs.next()) {
            reply one_reply = new reply();

            one_reply.setId(rs.getInt("Id"));
            one_reply.setTopic_id(rs.getString("topic_id"));
            one_reply.setReply_uid(rs.getInt("reply_uid"));
            one_reply.setContent(rs.getString("content"));
            one_reply.setCreate_time(rs.getString("create_time"));

            reply_list.add(one_reply);
        }

        rs.close();
        pstmt.close();
        conn.close();

        return reply_list;
    }

    /*将新的回复储存进表中，参数为新的回复对象*/
    public void NewReply(reply new_reply) throws SQLException {
        Connection conn = DBConn.getCon();
        PreparedStatement pstmt = null;
        String sql = "";

        sql = "insert into reply values(";
        sql = sql + "default,'" + new_reply.getTopic_id() + "','" + new_reply.getReply_uid() + "',";
        sql = sql + "'" + new_reply.getContent() + "',now())";

        pstmt = conn.prepareStatement(sql);
        pstmt.executeUpdate();

        /*更新帖子的最后更新时间*/
        sql = "update topic set `update_time` = now(),reply_num=reply_num+1 ";
        sql = sql + "where `Id` = " + new_reply.getTopic_id() +"";
        pstmt = null;
        pstmt = conn.prepareStatement(sql);
        pstmt.executeUpdate();

        pstmt.close();
        conn.close();
    }

    /*删除回复，根据回复id*/
    public void DelReply(String replyId) throws SQLException {
        Connection conn = DBConn.getCon();
        PreparedStatement pstmt = null;
        String sql = "";

        sql = "delete from reply where `Id` = " + replyId + "";

        pstmt = conn.prepareStatement(sql);
        pstmt.executeUpdate();

        pstmt.close();
        conn.close();
    }

    /*获取帖子的id获取帖子的回复*/
    public ArrayList getReplyListById(String topicId) throws SQLException {
        ArrayList replyList = new ArrayList();
        Connection conn = DBConn.getCon();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "";

        sql = "select reply.Id,reply.topic_id,reply_uid,reply.content,reply.create_time,users.username,users.avatar ";
        sql = sql + "from reply,users where reply.topic_id="+topicId+" and users.Id = reply.reply_uid ";
        sql = sql + "order by reply.create_time ASC";
        
        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery();
        
        while(rs.next()){
            reply one_reply = new reply();
            
            one_reply.setId(rs.getInt("reply.Id"));
            one_reply.setTopic_id(rs.getString("reply.topic_id"));
            one_reply.setReply_uid(rs.getInt("reply_uid"));
            one_reply.setContent(rs.getString("reply.content"));
            one_reply.setCreate_time(rs.getString("reply.create_time"));
            one_reply.setUsername(rs.getString("users.username"));
            one_reply.setUserAvatar(rs.getString("users.avatar"));
            
            replyList.add(one_reply);
        }

        rs.close();
        pstmt.close();
        conn.close();

        return replyList;
    }
    
    public ArrayList getTopicListByUserId(int userId) throws SQLException{
        ArrayList replyList = new ArrayList();
        Connection conn = DBConn.getCon();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "";
        
        sql = "select reply.Id,reply.topic_id,reply_uid,reply.content,reply.create_time,users.username,users.avatar ";
        sql = sql + "from reply,users where reply.reply_uid="+userId+" and users.Id = reply.reply_uid ";
        sql = sql + "order by reply.create_time ASC";
        
        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery();
        
        while(rs.next()){
            reply one_reply = new reply();
            
            one_reply.setId(rs.getInt("reply.Id"));
            one_reply.setTopic_id(rs.getString("reply.topic_id"));
            one_reply.setReply_uid(rs.getInt("reply_uid"));
            one_reply.setContent(rs.getString("reply.content"));
            one_reply.setCreate_time(rs.getString("reply.create_time"));
            one_reply.setUsername(rs.getString("users.username"));
            one_reply.setUserAvatar(rs.getString("users.avatar"));
            
            replyList.add(one_reply);
        }
        
        rs.close();
        pstmt.close();
        conn.close();

        return replyList;
    }
}
