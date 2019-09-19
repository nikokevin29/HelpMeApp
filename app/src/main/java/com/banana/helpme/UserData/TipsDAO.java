package com.banana.helpme.UserData;

public class TipsDAO {
    String title,desctiption,img;

    public String getTitle() { return title;}
    public void setTitle(String title) { this.title = title;}
    public String getDescription() { return desctiption;}
    public void setDesctiption(String description) { this.desctiption = description;}
    public String getImg(){ return img;}
    public void setImg(String img){ this.img = img;}

    public TipsDAO(){}
    public TipsDAO(String title,String description,String img)
    {
        this.title = title ;
        this.img = img;
        this.desctiption = description;
    }
}
