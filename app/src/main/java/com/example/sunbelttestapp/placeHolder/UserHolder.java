package com.example.sunbelttestapp.placeHolder;

import com.example.sunbelttestapp.info.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserHolder {
    @GET("users")
    Call<List<User>> getUsers();
}
