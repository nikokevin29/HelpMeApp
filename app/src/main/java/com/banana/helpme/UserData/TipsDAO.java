package com.banana.helpme.UserData;

public class TipsDAO {
    String title,description,img,username,datetime,id;
    public String getId() {
        return id;
    }
    public String getTitle() { return title;}
    public void setTitle(String title) { this.title = title;}
    public String getDescription() { return description;}
    public void setDesctiption(String description) { this.description = description;}
    public String getImg(){ return img;}
    public void setImg(String img){ this.img = img;}
    public String getUsername() { return username; }
    public void setUsername() { this.username = username;}
    public String getTime() { return datetime;}
    public void setTime() { this.datetime = datetime;}
    public TipsDAO(){}
    public TipsDAO(String title,String description,String img, String username, String datetime, String id)
    {
        this.title = title ;
        this.img = img;
        this.description = description;
        this.username = username;
        this.datetime = datetime;
        this.id = id;
    }
}
