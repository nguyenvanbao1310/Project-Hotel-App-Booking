package com.example.hotel_project.api;

import com.example.hotel_project.model.AccountDTO;
import com.example.hotel_project.model.LoginRequest;
import com.example.hotel_project.model.LoginResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoginApiService {
    @POST("/api/account/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
    @POST("/api/account/forgot-password")
    Call<Map<String, String>> forgotPassword(@Body Map<String, String> emailRequest);

    @POST("/api/account/reset-password")
    Call<String> resetPassword(@Body Map<String, String> request);



}
