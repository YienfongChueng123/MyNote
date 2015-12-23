package ShiTiLei;

import java.io.Serializable;

/**
 * Created by zyf on 2015/10/30.
 */
public class note implements Serializable {
    private int id;
    private int TopId;
    private String title;
    private String time;
    private String content;
    public note(){

    }
    public note(int id,int TopId,String title,String time,String content){
        this.id=id;
        this.TopId=TopId;
        this.title=title;
        this.time=time;
        this.content=content;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTime(){
        return time;
    }
    public void setTime(String time){
        this.time=time;
    }
    public String getContent(){
        return content;
    }
    public void setContent(String content){
        this.content=content;
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }
    public int getTopId(){return TopId; }
    public void setTopId(int TopId){
        this.TopId=TopId;
    }

}
