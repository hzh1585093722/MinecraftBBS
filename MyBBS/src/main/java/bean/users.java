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

//用户实体
public class users {
    private int id;
    private String username;
    private String password;
    private int group;/*权限组*/
    private String email;
    private String phone;
    private String qq;
    private int sex;
    private String birth;
    private String avatar;/*保存用户的头像路径*/
    private String create_time;
    private String update_time;
    private int is_banned;
    private int job;/*管理组职位*/
    
    public users(){}
    
    
    public void setId(int id)
    {
        this.id = id;
    }   
    public int getId()
    {
        return this.id;
    }
    
    
    public void setUsername(String username)
    {
        this.username = username;
    }  
    public String getUsername()
    {
        return this.username;
    }
    
    
    
    public void setPassword(String password)
    {
        this.password = password;
    }
    public String getPassword()
    {
        return this.password;
    }
    
    public void setGroup(int group)
    {
        this.group = group;
    }
    public int getGroup()
    {
        return this.group;
    }
    
    
    public void setEmail(String email)
    {
        this.email = email;
    }
    public String getEmail()
    {
        return this.email;
    }
    
    
    public void setPhone(String phone)
    {
        this.phone = phone;
    }
    public String getPhone()
    {
        return this.phone;
    }
    
    
    public void setQQ(String qq)
    {
        this.qq = qq;
    }
    public String getQQ()
    {
        return this.qq;
    }
    /*0私密，1男，2女*/
    public void setSex(int sex){
        this.sex = sex;
    }
    
    public int getSex(){
        return this.sex;
    }
    
    public void setBirth(String birth)
    {
        this.birth = birth;
    }
    public String getBirth()
    {
        return this.birth;
    }
    
    
    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }
    public String getAvatar()
    {
        return this.avatar;
    }
    
    
    public void setCreate_time(String create_time)
    {
        this.create_time = create_time;
    }
    public String getCreate_time()
    {
        return this.create_time;
    }
    
    public void setUpdate_time(String update_time)
    {
        this.update_time = update_time;
    }
    public String getUpdate_time()
    {
        return this.update_time;
    }
    
    
    public void setIs_banned(int is_banned)
    {
        this.is_banned = is_banned;
    }
    public int getIs_banned()
    {
        return this.is_banned;
    }
    
    /*管理组职位设置*/
    public void setJob(int job){
        this.job = job;
    }
    
    public int getJob(){
        return this.job;
    }
}