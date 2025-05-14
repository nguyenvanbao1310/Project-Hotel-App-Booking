package com.example.hotel_project.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import java.util.Map;

public interface OtpApiService {

    @POST("/api/account/verify-otp")
    Call<Void> verifyOtp(@Body Map<String, String> request);
    @POST("/api/account/resend-otp")
    Call<Map<String, String>> resendOtp(@Body Map<String, String> emailRequest);

}