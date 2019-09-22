package com.banana.helpme.UserData;

import java.util.Date;

public class ReportDAO {
    String id, username, datetime, kategori, img, description, alamat;

    public ReportDAO() {}

    public ReportDAO(String id, String username, String datetime, String kategori, String img, String description, String alamat) {
        this.id = id;
        this.username = username;
        this.datetime = datetime;
        this.kategori = kategori;
        this.img = img;
        this.description = description;
        this.alamat = alamat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
