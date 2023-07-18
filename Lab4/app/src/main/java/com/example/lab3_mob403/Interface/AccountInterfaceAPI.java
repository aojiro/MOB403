package com.example.lab3_mob403.Interface;

import com.example.lab3_mob403.APIServer.ServerResponseAccount;
import com.example.lab3_mob403.APIServer.ServerResponseMessageChangePassword;
import com.example.lab3_mob403.APIServer.ServerResponseSelectAccount;

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
    @POST("check_login_selectdata.php")
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
}
