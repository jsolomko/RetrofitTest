package com.example.mytestretrofit.api;

import com.example.mytestretrofit.models.LoginResponse;
import com.example.mytestretrofit.models.Post;
import com.example.mytestretrofit.models.UserAuth;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {

    @GET("posts")
    Call<List<Post>> getUser();

    @POST("posts")
    Call<Post> createPost(@Body Post post);

    @POST("auth/login")
    Call<LoginResponse> login(@Body UserAuth userAuth);


    @FormUrlEncoded
    @POST("auth/login")
    Call<List<LoginResponse>> auth1(
            @Field("name") String username,
            @Field("password") String password
    );


    @FormUrlEncoded
    @POST("auth/login")
    Call<UserAuth> authMap(@FieldMap Map<String, String> fields);

    @PUT("posts/{id}")
    Call<Post> putPost(@Path("id") int id, @Body Post post);

    @PATCH("posts/{id}")
    Call<Post> patchPost(@Path("id") int id, @Body Post post);


}
