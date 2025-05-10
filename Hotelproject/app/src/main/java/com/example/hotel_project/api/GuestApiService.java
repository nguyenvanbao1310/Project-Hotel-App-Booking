package com.example.hotel_project.api;


import com.example.hotel_project.model.GuestDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GuestApiService {
    @GET("api/user/{account_id}") // Đặt đúng path của API bên bạn
    Call<GuestDTO> getGuestById(@Path("account_id") String account_id);
}
