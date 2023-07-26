package com.example.lab5.Interface;

import com.example.lab5.RetrofitServer.ServerDeleteProduct;
import com.example.lab5.RetrofitServer.ServerInsertProduct;
import com.example.lab5.RetrofitServer.ServerSelectProduct;
import com.example.lab5.RetrofitServer.ServerUpdateProduct;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ProductAPI {
    @FormUrlEncoded
    @POST("insert_product.php")
    Call<ServerInsertProduct> insertProduct(
            @Field("name") String name,
            @Field("price") int price,
            @Field("description") String description
    );

    @GET("get_select_product.php")
    Call<ServerSelectProduct> selectProduct();

    @FormUrlEncoded
    @POST("delete_product_by_id.php")
    Call<ServerDeleteProduct> deleteProduct(
            @Field("id") int id
    );

    @FormUrlEncoded
    @POST("update_product.php")
    Call<ServerUpdateProduct> updateProduct(
            @Field("id") int id,
            @Field("name") String name,
            @Field("price") int price,
            @Field("description") String description
    );
}

