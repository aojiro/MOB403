package com.example.assignment.Interface;

import com.example.assignment.API_Server.ServerResponseAccount;
import com.example.assignment.API_Server.ServerResponseMessageChangePassword;
import com.example.assignment.API_Server.ServerResponseSelectAccount;
import com.example.assignment.API_Server.ServerResponseShowAccount;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AccountInterfaceAPI {


    @FormUrlEncoded
    @POST("insert_account.php")
    Call<ServerResponseAccount> insertAccount(
            @Field("name") String name,
            @Field("email") String email,
            @Field("encrypted_password") String encrypted_password
    );

    @FormUrlEncoded
    @POST("check_login.php")
    Call<ServerResponseSelectAccount> getSelectAccount(
            @Field("email") String email,
            @Field("encrypted_password") String encrypted_password
    );

    @FormUrlEncoded
    @POST("change_password_by_id.php")
    Call<ServerResponseMessageChangePassword> getMessageChangePassword(
            @Field("id") int id,
            @Field("new_password") String new_password
    );
    @FormUrlEncoded
    @POST("show_data.php")
    Call<ServerResponseShowAccount> getShowAccount(
            @Field("id") int id,
            @Field("email") String email,
            @Field("name") String name
    );
}