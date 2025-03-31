package com.example.hotel_project.Api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import java.util.Map;


public interface EmailApi
{
    @POST("verifyOtp")
    Call<String> verifyOtp(@Body Map<String, String> requestData);
}
