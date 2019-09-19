package com.banana.helpme.UserData;

public class UserDAO {
    String nama, telepon, email, username, password, tanggal_lahir, gender;

    public String getNama() { return nama;}
    public void setNama(String nama) { this.nama = nama;}
    public String getTelepon() { return telepon; }
    public void setTelepon(String telepon) { this.telepon = telepon; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email;}
    public String getUsername() {return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() {return password;}
    public void setPassword(String password) { this.password = password; }
    public String getTanggal_lahir() { return tanggal_lahir; }
    public void setTanggal_lahir(String tanggal_lahir) { this.tanggal_lahir = tanggal_lahir; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public UserDAO(){}
    public UserDAO(String nama, String telepon, String email, String username, String password, String tanggal_lahir, String gender) {
        this.nama = nama;
        this.telepon = telepon;
        this.email = email;
        this.username = username;
        this.password = password;
        this.tanggal_lahir = tanggal_lahir;
        this.gender = gender;
    }


}
