package com.example.hotel_project.api;

import com.example.hotel_project.model.ChangePasswordRequest;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AccountApiService {
    @POST("/api/account/change-password")
    Call<Map<String, String>> changePassword(@Body Map<String, String> request);


}
