package com.example.hotel_project.Api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import com.example.hotel_project.Model.User;

public interface UserApi
{
    @POST("register")
    Call<User> registerUser(@Body User user);

}

