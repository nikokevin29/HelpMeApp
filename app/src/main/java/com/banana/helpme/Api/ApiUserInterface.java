package com.banana.helpme.Api;

import com.banana.helpme.UserData.ReportDAO;
import com.banana.helpme.UserData.TipsDAO;
import com.banana.helpme.UserData.UserDAO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface ApiUserInterface {

    // BUAT PROFIL

    @POST("createProfile.php")
    @FormUrlEncoded
    Call<UserDAO> addUser(@Field("name")String nama,
                          @Field("phone")String telepon,
                          @Field("email")String email,
                          @Field("username")String username,
                          @Field("birth")String tanggal_lahir,
                          @Field("gender")String gender);

//    @GET("viewProfile.php/{email}")
 //   Call<UserDAO> getUser(@Path("email") String email);

    @GET("viewProfile.php")
    Call<List<UserDAO>> getAllUser();

    @POST("updateProfile.php")
    @FormUrlEncoded
    Call<UserDAO> editUser(@Field("email") String email,
                          @Field("name")String nama,
                          @Field("phone")String telepon,
                          @Field("username")String username,
                          @Field("birth")String tanggal_lahir,
                          @Field("gender")String gender);

//    @DELETE("Profile/deleteProfile/{id}")
//    Call<String> deleteUser(@Path("id") String id);

    // BUAT REPORT
    @FormUrlEncoded
    @POST("createReport.php")
    Call<ReportDAO> addReport(@Field("kategori")String kategori,
                         @Field("img")String img,
                         @Field("address")String alamat,
                         @Field("description")String description,
                           @Field("username")String username,
                           @Field("datetime")String datetime);

    @GET("viewReport.php")
    Call<List<ReportDAO>> getAllReport();

    @POST("updateReport.php")
    @FormUrlEncoded
    Call<ReportDAO> editReport(@Field("id") String id,
                            @Field("kategori")String kategori,
                            @Field("img")String img,
                            @Field("description")String description);

    @POST("deleteReport.php")
    @FormUrlEncoded
    Call<Void> deleteReport(@Field("id") String id);


    // BUAT Tips
    @FormUrlEncoded
    @POST("createTips.php")
    Call<TipsDAO> addTips(@Field("title")String title,
                           @Field("description")String description,
                           @Field("img")String img,
                         @Field("username")String username,
                         @Field("datetime")String datetime);

    @GET("viewTips.php")
    Call<List<TipsDAO>> getAllTips();

    @POST("updateTips.php")
    @FormUrlEncoded
    Call<TipsDAO> editTips(@Field("id") String id,
                          @Field("title")String title,
                          @Field("description")String description);

    @POST("deleteTips.php")
    @FormUrlEncoded
    Call<Void> deleteTips(@Field("id") String id);

}
