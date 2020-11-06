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
public class topic {

    private int id;
    private int user_id;
    private String create_time;
    private String update_time;
    private String title;
    private int tab_id;
    private String content;
    private int clicks;
    private int like;
    private int reply_num;
    private String username;/*用户表中的用户名*/
    private String avatar;/*用户头像*/

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_id() {
        return this.user_id;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getCreate_time() {
        return this.create_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getUpdate_time() {
        return this.update_time;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTab_id(int tab_id) {
        this.tab_id = tab_id;
    }

    public int getTab_id() {
        return this.tab_id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public int getClicks() {
        return this.clicks;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getLike() {
        return this.like;
    }

    public void setReply_num(int reply_num) {
        this.reply_num = reply_num;
    }

    public int getReply_num() {
        return this.reply_num;
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
