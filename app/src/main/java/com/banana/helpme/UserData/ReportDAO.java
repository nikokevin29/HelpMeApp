package com.banana.helpme.UserData;

public class ReportDAO {
    String kategori, img, desctiption;
    Float longitude, latitude;
    public String getKategori(){ return kategori;}
    public void setKategori(String kategori){ this.kategori = kategori;}
    public String getImg(){ return img;}
    public void setImg(String img){ this.img = img;}
    public Float getLongitude(){ return longitude;}
    public void setLongitude(Float longitude){ this.longitude = longitude;}
    public Float getLatitude(){ return latitude;}
    public void setLatitude(Float latitude){ this.latitude = latitude;}
    public String getDesctiption(){ return desctiption;}
    public void setDesctiption(String img){ this.desctiption = desctiption;}

    public ReportDAO() {}
    public ReportDAO(String kategori,String img, Float longitude,Float latitude, String desctiption)
    {
        this.kategori = kategori ;
        this.img = img;
        this.longitude = longitude;
        this.latitude = latitude;
        this.desctiption = desctiption;
    }
}
