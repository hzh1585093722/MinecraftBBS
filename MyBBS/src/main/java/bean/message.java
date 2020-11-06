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
public class message {
    private long id;
    private int sender_id;
    private int receiver_id;
    private int type;
    private String content;
    private String username;/*发送者，用户表中的用户名*/
    private String create_time;
    private String target_username;/*目标用户名,也在用户表中*/
    
    public void setId(long id){
        this.id = id;
    }
    
    public long getId(){
        return this.id;
    }
    
    public void setSender_id(int sender_id){
        this.sender_id = sender_id;
    }
    
    public int getSender_id(){
        return this.sender_id;
    }
    
    public void setReceiver_id(int receiver_id){
        this.receiver_id = receiver_id;
    }
    
    public int getReceiver_id(){
        return this.receiver_id;
    }
    
    public void setType(int type){
        this.type = type;
    }
    
    public int getType(){
        return this.type;
    }
    
    public void setContent(String content){
        this.content = content;
    }
    
    public String getContent(){
        return this.content;
    }
    
    public void setUsername(String username){
        this.username = username;
    }
    
    public String getUsername(){
        return this.username;
    }
    
    public void setCreate_time(String create_time){
        this.create_time = create_time;
    }
    
    public String getCreate_time(){
        return this.create_time;
    }
    
    public void setTarget_username(String target_username){
        this.target_username = target_username;
    }
    
    public String getTarget_username(){
        return this.target_username;
    }
}
