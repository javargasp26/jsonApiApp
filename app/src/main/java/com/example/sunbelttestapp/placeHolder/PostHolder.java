package com.example.sunbelttestapp.placeHolder;

import com.example.sunbelttestapp.info.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PostHolder {
    @GET("posts")
    Call<List<Post>> getPost();
}
