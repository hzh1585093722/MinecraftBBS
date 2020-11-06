/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import bean.topic;
import java.util.ArrayList;
import JDBC_connector.DBConn;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author 15850 获取帖子信息，储存进集合中
 */
public class topic_dao {

    /*
*本来想声明为静态方法的，省去创建对象的步骤，但是静态方法只能被继承不能被重写，
* 如果以后子类要改dao类的逻辑，还得回原始的类去修改
     */
 /*获取所有帖子不分板块*/
    public ArrayList getTopicList() throws SQLException {

        ArrayList topic_list = new ArrayList();
        Connection conn = DBConn.getCon();
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        String sql = "";

        /*字符串拼接时注意sql语句的空格*/
        sql = "select topic.Id,users.username,user_id,topic.create_time,topic.update_time, ";
        sql = sql + "title,tab_id,content,clicks,`like`,reply_num from topic,users ";
        sql = sql + "where user_id = users.Id ";
        
        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery();

        while (rs.next()) {
            topic one_topic = new topic();

            one_topic.setId(rs.getInt("id"));
            one_topic.setUser_id(rs.getInt("user_id"));
            one_topic.setCreate_time(rs.getString("create_time"));
            one_topic.setUpdate_time(rs.getString("update_time"));
            one_topic.setTitle(rs.getString("title"));
            one_topic.setTab_id(rs.getInt("tab_id"));
            one_topic.setContent(rs.getString("content"));
            one_topic.setClicks(rs.getInt("clicks"));
            one_topic.setLike(rs.getInt("like"));
            one_topic.setReply_num(rs.getInt("reply_num"));
            one_topic.setUsername(rs.getString("users.username"));

            topic_list.add(one_topic);
        }

        rs.close();
        pstmt.close();
        conn.close();

        return topic_list;
    }

    /*根据板块获取帖子信息，方法参数为板块的英文名*/
    public ArrayList getTopicListByTabName(String tab_name_en) throws SQLException {

        ArrayList topic_list = new ArrayList();
        Connection conn = DBConn.getCon();
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        String sql = "";

        /*字符串拼接时注意sql语句的空格*/
        sql = "select topic.Id,users.username,user_id,topic.create_time,topic.update_time,";
        sql = sql + "title,tab_id,content,clicks,`like`,reply_num from topic,users ";
        sql = sql + "where tab_id in(";
        sql = sql + "select tab.Id from tab where ";
        sql = sql + "tab_name_en = '" + tab_name_en + "') ";
        sql = sql + "and users.id = user_id";

        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery();

        while (rs.next()) {
            topic one_topic = new topic();

            one_topic.setId(rs.getInt("topic.Id"));
            one_topic.setUser_id(rs.getInt("user_id"));
            one_topic.setCreate_time(rs.getString("topic.create_time"));
            one_topic.setUpdate_time(rs.getString("topic.update_time"));
            one_topic.setTitle(rs.getString("title"));
            one_topic.setTab_id(rs.getInt("tab_id"));
            one_topic.setContent(rs.getString("content"));
            one_topic.setClicks(rs.getInt("clicks"));
            one_topic.setLike(rs.getInt("like"));
            one_topic.setReply_num(rs.getInt("reply_num"));
            one_topic.setUsername(rs.getString("users.username"));

            topic_list.add(one_topic);
        }

        rs.close();
        pstmt.close();
        conn.close();

        return topic_list;
    }

    /*
    获取指定数目的最新的帖子，用于在首页显示,number为要显示的条目,
    tab_name_en为板块英文名
     */
    public ArrayList getSomeNewestTopicList(String number, String tab_name_en) throws SQLException {
        ArrayList topic_list = new ArrayList();
        Connection conn = DBConn.getCon();
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        String sql = "";

        /*字符串拼接时注意sql语句的空格*/
        sql = "select topic.Id,users.username,user_id,topic.create_time,topic.update_time,";
        sql = sql + "title,tab_id,content,clicks,`like`,reply_num from topic,users where ";
        sql = sql + "tab_id in(select Id from tab where tab_name_en = '" + tab_name_en + "') ";
        sql = sql + "and users.id = user_id ";
        sql = sql + "limit " + number + "";

        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery();

        while (rs.next()) {
            topic one_topic = new topic();

            one_topic.setId(rs.getInt("id"));
            one_topic.setUser_id(rs.getInt("user_id"));
            one_topic.setCreate_time(rs.getString("create_time"));
            one_topic.setUpdate_time(rs.getString("update_time"));
            one_topic.setTitle(rs.getString("title"));
            one_topic.setTab_id(rs.getInt("tab_id"));
            one_topic.setContent(rs.getString("content"));
            one_topic.setClicks(rs.getInt("clicks"));
            one_topic.setLike(rs.getInt("like"));
            one_topic.setReply_num(rs.getInt("reply_num"));
            one_topic.setUsername(rs.getString("users.username"));

            topic_list.add(one_topic);
        }

        rs.close();
        pstmt.close();
        conn.close();

        return topic_list;
    }

    /*根据用户id来获取该用户的所有帖子*/
    public ArrayList viewTopicListByUserId(int uid) throws SQLException {

        ArrayList topic_list = new ArrayList();
        Connection conn = DBConn.getCon();
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        String sql = "";

        /*字符串拼接时注意sql语句的空格*/
        sql = "select `id`,`user_id`,`create_time`,`update_time`,";
        sql = sql + "`title`,`tab_id`,`content`,`clicks`,`like`,";
        sql = sql + "`reply_num` from topic where ";
        sql = sql + "`user_id` = " + uid;

        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery();

        while (rs.next()) {
            topic one_topic = new topic();

            one_topic.setId(rs.getInt("id"));
            one_topic.setUser_id(rs.getInt("user_id"));
            one_topic.setCreate_time(rs.getString("create_time"));
            one_topic.setUpdate_time(rs.getString("update_time"));
            one_topic.setTitle(rs.getString("title"));
            one_topic.setTab_id(rs.getInt("tab_id"));
            one_topic.setContent(rs.getString("content"));
            one_topic.setClicks(rs.getInt("clicks"));
            one_topic.setLike(rs.getInt("like"));
            one_topic.setReply_num(rs.getInt("reply_num"));

            topic_list.add(one_topic);
        }

        rs.close();
        pstmt.close();
        conn.close();

        return topic_list;
    }

    /*新建帖子,参数为一个topic对象,并将该对象的值插入到表中*/
    public void NewTopic(topic new_topic) throws SQLException {
        Connection conn = DBConn.getCon();
        PreparedStatement pstmt = null;
        String sql = "";

        sql = "insert into topic values(default,";
        sql = sql + "'" + new_topic.getUser_id() + "',";
        sql = sql + "now(),now(),'" + new_topic.getTitle() + "',";
        sql = sql + "'" + new_topic.getTab_id() + "',";
        sql = sql + "'" + new_topic.getContent() + "',";
        sql = sql + "default,default,default)";

        pstmt = conn.prepareStatement(sql);
        pstmt.executeUpdate();

        pstmt.close();
        conn.close();
    }

    /*通过帖子的id删除掉该帖子*/
    public void delTopicById(String topic_id) throws SQLException {
        Connection conn = DBConn.getCon();
        PreparedStatement pstmt = null;
        String sql = "";



        sql = "delete from topic where `id` = " + topic_id;
        
        pstmt = conn.prepareStatement(sql);
        pstmt.executeUpdate();

        pstmt.close();
        conn.close();
    }

    /*帖子的搜索功能，参数为用户在搜索框输入的东西*/
    public ArrayList getTopicListBySearch(String user_input) throws SQLException {

        ArrayList topic_list = new ArrayList();
        Connection conn = DBConn.getCon();
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        String sql = "";

        /*字符串拼接时注意sql语句的空格*/
        sql = "select `id`,`user_id`,`create_time`,`update_time`,";
        sql = sql + "`title`,`tab_id`,`content`,`clicks`,`like`,";
        sql = sql + "`reply_num` from topic where ";
        sql = sql + "`title` like '%" + user_input + "%' or `content` like '%" + user_input + "%'";

        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery();

        while (rs.next()) {
            topic one_topic = new topic();

            one_topic.setId(rs.getInt("id"));
            one_topic.setUser_id(rs.getInt("user_id"));
            one_topic.setCreate_time(rs.getString("create_time"));
            one_topic.setUpdate_time(rs.getString("update_time"));
            one_topic.setTitle(rs.getString("title"));
            one_topic.setTab_id(rs.getInt("tab_id"));
            one_topic.setContent(rs.getString("content"));
            one_topic.setClicks(rs.getInt("clicks"));
            one_topic.setLike(rs.getInt("like"));
            one_topic.setReply_num(rs.getInt("reply_num"));

            topic_list.add(one_topic);
        }

        rs.close();
        pstmt.close();
        conn.close();

        return topic_list;
    }

    /*获取帖子的信息*/
    public topic getTopicInfoById(String topicId) throws SQLException {
        topic one_topic = new topic();
        Connection conn = DBConn.getCon();
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        String sql = "";

        sql = "select topic.Id,topic.user_id,topic.create_time,topic.update_time,topic.title,topic.tab_id,";
        sql = sql + "topic.content,topic.clicks,`like`,topic.reply_num,users.username,users.avatar from topic,users ";
        sql = sql + "where topic.Id = "+topicId+" and users.Id = topic.user_id";

        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery();

        if (rs.next()) {
            
            one_topic.setId(rs.getInt("topic.Id"));
            one_topic.setUser_id(rs.getInt("topic.user_id"));
            one_topic.setCreate_time(rs.getString("topic.create_time"));
            one_topic.setUpdate_time(rs.getString("topic.update_time"));
            one_topic.setTitle(rs.getString("topic.title"));
            one_topic.setTab_id(rs.getInt("topic.tab_id"));
            one_topic.setContent(rs.getString("topic.content"));
            one_topic.setClicks(rs.getInt("topic.clicks"));
            one_topic.setLike(rs.getInt("like"));
            one_topic.setUsername(rs.getString("users.username"));
            one_topic.setUserAvatar(rs.getString("users.avatar"));
        }

        rs.close();
        pstmt.close();
        conn.close();

        return one_topic;
    }
    
    /*根据板块id获取帖子*/
    public ArrayList getTopicListById(int topic_id) throws SQLException{
         ArrayList topic_list = new ArrayList();
        Connection conn = DBConn.getCon();
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        String sql = "";

        /*字符串拼接时注意sql语句的空格*/
        sql = "select topic.Id,users.username,user_id,topic.create_time,topic.update_time, ";
        sql = sql + "title,tab_id,content,clicks,`like`,reply_num from topic,users ";
        sql = sql + "where tab_id = "+topic_id+" and user_id = users.Id ";

        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery();

        while (rs.next()) {
            topic one_topic = new topic();

            one_topic.setId(rs.getInt("id"));
            one_topic.setUser_id(rs.getInt("user_id"));
            one_topic.setCreate_time(rs.getString("create_time"));
            one_topic.setUpdate_time(rs.getString("update_time"));
            one_topic.setTitle(rs.getString("title"));
            one_topic.setTab_id(rs.getInt("tab_id"));
            one_topic.setContent(rs.getString("content"));
            one_topic.setClicks(rs.getInt("clicks"));
            one_topic.setLike(rs.getInt("like"));
            one_topic.setReply_num(rs.getInt("reply_num"));
            one_topic.setUsername(rs.getString("users.username"));

            topic_list.add(one_topic);
        }

        rs.close();
        pstmt.close();
        conn.close();

        return topic_list;
    }
}
