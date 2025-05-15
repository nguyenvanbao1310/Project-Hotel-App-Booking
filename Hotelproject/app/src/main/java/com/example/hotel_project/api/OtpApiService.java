package com.example.hotel_project.api;

import com.example.hotel_project.model.ApiResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import java.util.Map;

public interface OtpApiService {

    @POST("/api/account/verify-otp")
    Call<Void> verifyOtp(@Body Map<String, String> request);
    @POST("/api/account/resend-otp")
    Call<Map<String, String>> resendOtp(@Body Map<String, String> emailRequest);
    @POST("/api/account/xacnhan-otp")
    Call<Void> XacNhanOtp(@Body Map<String, String> request);
    @POST("/api/account/reset-password")
    Call<Map<String, String>> resetPassword(@Body Map<String, String> request);




}