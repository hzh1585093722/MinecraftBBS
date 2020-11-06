/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

/**
 *
 * @author 15850
 */
public class reply {

    private long id;
    private String topic_id;
    private int reply_uid;
    private String content;
    private String create_time;
    private String username;/*用户表中的用户名*/
    private String avatar;/*用户的头像地址*/

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return this.id;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
    }

    public String getTopic_id() {
        return this.topic_id;
    }

    public void setReply_uid(int reply_uid) {
        this.reply_uid = reply_uid;
    }

    public int getReply_uid() {
        return this.reply_uid;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
    
    public String getCreate_time(){
        return this.create_time;
    }
    
    public void setUsername(String username){
        this.username = username;
    }
    
    public String getUsername(){
        return this.username;
    }
    
    public void setUserAvatar(String avatar){
        this.avatar = avatar;
    }
    
    public String getUserAvatar(){
        return this.avatar;
    }
}
