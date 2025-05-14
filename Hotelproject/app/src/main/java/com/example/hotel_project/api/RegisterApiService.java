package com.example.hotel_project.api;

import com.example.hotel_project.model.RegisterRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegisterApiService {
    @POST("/api/account/register")
    Call<Void> register(@Body RegisterRequest request);
}
