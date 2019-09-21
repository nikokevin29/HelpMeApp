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
    Call<String> addUser(@Field("name")String nama,
                          @Field("phone")String telepon,
                          @Field("email")String email,
                          @Field("username")String username,
                          @Field("birth")String tanggal_lahir,
                          @Field("gender")String gender);

//    @GET("viewProfile.php/{email}")
 //   Call<UserDAO> getUser(@Path("email") String email);

    @GET("viewProfile.php")
    Call<List<UserDAO>> getAllUser();

    @PUT("updateProfile.php")
    @FormUrlEncoded
    Call<String> editUser(@Path("id") String id,
                          @Field("name")String nama,
                          @Field("phone")String telepon,
                          @Field("email")String email,
                          @Field("username")String username,
                          @Field("birth")String tanggal_lahir,
                          @Field("gender")String gender);

//    @DELETE("Profile/deleteProfile/{id}")
//    Call<String> deleteUser(@Path("id") String id);

    // BUAT REPORT
    @FormUrlEncoded
    @POST("createReport.php")
    Call<String> addReport(@Field("kategori")String kategori,
                         @Field("img")String img,
                         @Field("address")String alamat,
                         @Field("description")String description,
                           @Field("username")String username,
                           @Field("datetime")String datetime);

    @GET("viewReport.php")
    Call<List<ReportDAO>> getAllReport();

    @PUT("updateReport.php")
    @FormUrlEncoded
    Call<String> editReport(@Path("id") String id,
                            @Field("kategori")String kategori,
                            @Field("img")String img,
                            @Field("address")String alamat,
                            @Field("description")String description,
                            @Field("username")String username,
                            @Field("datetime")String datetime);

    @DELETE("deleteReport.php")
    Call<String> deleteReport(@Path("id") String id);


    // BUAT Tips

    @FormUrlEncoded
    @POST("createTips.php")
    Call<String> addTips(@Field("title")String title,
                           @Field("description")String description,
                           @Field("img")String img,
                         @Field("username")String username,
                         @Field("datetime")String datetime);

    @GET("viewTips.php")
    Call<List<TipsDAO>> getAllTips();

    @PUT("updateTips.php")
    @FormUrlEncoded
    Call<String> editTips(@Path("id") String id,
                          @Field("title")String title,
                          @Field("description")String description,
                          @Field("img")String img,
                          @Field("username")String username,
                          @Field("datetime")String datetime);

    @DELETE("deleteTips.php")
    Call<String> deleteTips(@Path("id") String id);

}
