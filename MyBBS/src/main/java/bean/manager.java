/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

/**
 *
 * @author 15850
 * 管理人员职位
 */
public class manager {
    private int Id;
    private int uid;
    private int job;/*0为超管，正整数为被管理板块的id*/
    
    public void setId(int id){
        this.Id = id;               
    }
    
    public int getId(){
        return this.Id;
    }
    
    public void setUID(int uid){
        this.uid = uid;
    }
    
    public int getUID(){
        return this.uid;
    }
    
    public void setJob(int job){
        this.job = job;
    }
    
    public int getJob(){
        return this.job;
    }
}
