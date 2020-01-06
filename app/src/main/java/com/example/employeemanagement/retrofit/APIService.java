package com.example.employeemanagement.retrofit;

import com.example.employeemanagement.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {

    @FormUrlEncoded
    @POST("dangnhap.php")
    Call<ArrayList<User>> login(@Field("username") String username, @Field("password") String password);

    @GET("getdatanhanvien.php")
    Call<ArrayList<User>> getAllUsers();

    @FormUrlEncoded
    @POST("xoa.php")
    Call<String> delEmployee(@Field("id") int id);

    @FormUrlEncoded
    @POST("capnhat.php")
    Call<String> updateInfo(@Field("id") int id,
                            @Field("name") String name,
                            @Field("phone") String phone,
                            @Field("position") String position,
                            @Field("department") String department);

}
