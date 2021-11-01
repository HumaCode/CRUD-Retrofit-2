package com.example.uts_humacode.API;

import com.example.uts_humacode.Model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIRequestData {
//    mengambil data / tampil data  dari webservice / php
    @GET("retrieve.php")
    Call<ResponseModel> ardRetrieveData();

//    cread data
    @FormUrlEncoded
    @POST("create.php")
    Call<ResponseModel> ardCreateData(
            @Field("nama") String nama,
            @Field("alamat") String alamat,
            @Field("telepon") String telepon
    );

//    delete data
    @FormUrlEncoded
    @POST("delete.php")
    Call<ResponseModel> ardDelete(
            @Field("id") int id
    );

//    get data by id
    @FormUrlEncoded
    @POST("get.php")
    Call<ResponseModel> ardGetData(
            @Field("id") int id
    );

//    update data
    @FormUrlEncoded
    @POST("update.php")
    Call<ResponseModel> ardUpdateData(
            @Field("id") int id,
            @Field("nama") String nama,
            @Field("alamat") String alamat,
            @Field("telepon") String telepon
    );
}
