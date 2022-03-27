package com.example.mytestretrofit.api;

import com.example.mytestretrofit.models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {
    @GET("posts")
    Call<List<Post>> getUser();

}
