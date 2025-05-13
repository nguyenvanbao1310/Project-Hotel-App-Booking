package com.example.hotel_project.api;

import com.example.hotel_project.model.AccountDTO;
import com.example.hotel_project.model.LoginRequest;
import com.example.hotel_project.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginApiService {
    @POST("/api/account/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
}
