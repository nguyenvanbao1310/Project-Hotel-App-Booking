package com.example.hotel_project.api;

import com.example.hotel_project.model.AccountDTO;
import com.example.hotel_project.model.LoginRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginApiService {
    @POST("/api/account/login")
    Call<AccountDTO> login(@Body LoginRequest loginRequest);
}
