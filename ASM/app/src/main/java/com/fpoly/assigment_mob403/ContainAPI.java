package com.fpoly.assigment_mob403;

import com.fpoly.assigment_mob403.API.API;
import com.fpoly.assigment_mob403.API.API_Comment;
import com.fpoly.assigment_mob403.API.API_Story;
import com.fpoly.assigment_mob403.API.API_User;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContainAPI {
    // Định nghĩa các đường dẫn API
    public static final String GetAll = "/";
    public static final String GetElement = "/{id}";
    public static final String GetByName = "/getByName/{ten}";
    public static final String CreateElement = "/create";
    public static final String UpdateElement = "/update/{id}";
    public static final String DeleteElement = "/delete/{id}";
    public static final String DeleteAll = "/delete";

    // Địa chỉ URL của API
    public static final String URL = "http://192.168.1.6:9000/";

    // Khai báo các đối tượng API
    private static API_Comment API_COMMENT;
    private static API_Story API_STORY;
    private static API_User API_USER;
    private static API _API;
    private static Retrofit retrofit;

    // Lấy đối tượng Retrofit
    private static Retrofit GetRetrofit(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    // Lấy đối tượng API_Story
    public static API_Story STORY(){
        if(API_STORY == null){
            API_STORY = GetRetrofit().create(API_Story.class);
        }
        return API_STORY;
    }

    // Lấy đối tượng API_Comment
    public static API_Comment COMMENT(){
        if(API_COMMENT == null){
            API_COMMENT = GetRetrofit().create(API_Comment.class);
        }
        return API_COMMENT;
    }

    // Lấy đối tượng API_User
    public static API_User USER(){
        if(API_USER == null){
            API_USER = GetRetrofit().create(API_User.class);
        }
        return API_USER;
    }

    // Lấy đối tượng API
    public static API API(){
        if(_API == null) _API = GetRetrofit().create(API.class);
        return _API;
    }
}
