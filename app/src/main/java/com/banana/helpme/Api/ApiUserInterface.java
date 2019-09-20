package com.banana.helpme.Api;

import com.banana.helpme.UserData.ReportDAO;
import com.banana.helpme.UserData.TipsDAO;
import com.banana.helpme.UserData.UserDAO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface ApiUserInterface {

    // BUAT PROFIL
    @FormUrlEncoded
    @POST("Profile/createProfile")
    Call<String> addUser(@Field("name")String nama,
                          @Field("phone")String telepon,
                          @Field("email")String email,
                          @Field("username")String username,
                          @Field("password")String password,
                          @Field("birth")String tanggal_lahir,
                          @Field("gender")String gender);

    @GET("Profile/getByEmail/{email}")
    Call<UserDAO> getUser(@Path("email") String email);

    @GET("Profile/getProfile")
    Call<List<UserDAO>> getAllUser();

    @PUT("Profile/updateProfile/{id}")
    @FormUrlEncoded
    Call<String> editUser(@Path("id") String id,
                          @Field("name")String nama,
                          @Field("phone")String telepon,
                          @Field("email")String email,
                          @Field("username")String username,
                          @Field("password")String password,
                          @Field("birth")String tanggal_lahir,
                          @Field("gender")String gender);

    @DELETE("Profile/deleteProfile/{id}")
    Call<String> deleteUser(@Path("id") String id);

    // BUAT REPORT
    @FormUrlEncoded
    @POST("Report/createReport")
    Call<String> addReport(@Field("kategori")String kategori,
                         @Field("img")String img,
                         @Field("address")String alamat,
                         @Field("description")String description,
                           @Field("username")String username,
                           @Field("datetime")String datetime);

    @GET("Report/getReport")
    Call<List<ReportDAO>> getAllReport();

    @PUT("Report/updateReport/{id}")
    @FormUrlEncoded
    Call<String> editReport(@Path("id") String id,
                            @Field("kategori")String kategori,
                            @Field("img")String img,
                            @Field("address")String alamat,
                            @Field("description")String description,
                            @Field("username")String username,
                            @Field("datetime")String datetime);

    @DELETE("Report/deleteReport/{id}")
    Call<String> deleteReport(@Path("id") String id);


    // BUAT Tips

    @FormUrlEncoded
    @POST("Tips/createTips")
    Call<String> addTips(@Field("title")String title,
                           @Field("description")String description,
                           @Field("img")String img,
                         @Field("username")String username,
                         @Field("datetime")String datetime);

    @GET("Tips/getTips")
    Call<List<TipsDAO>> getAllTips();

    @PUT("Tips/updateTips/{id}")
    @FormUrlEncoded
    Call<String> editTips(@Path("id") String id,
                          @Field("title")String title,
                          @Field("description")String description,
                          @Field("img")String img,
                          @Field("username")String username,
                          @Field("datetime")String datetime);

    @DELETE("Tips/deleteTips/{id}")
    Call<String> deleteTips(@Path("id") String id);

}
