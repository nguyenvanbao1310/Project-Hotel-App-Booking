package com.example.hotel_project.api;

import com.example.hotel_project.model.Hotel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface HotelApiService {
    @GET("/api/hotels")
    Call<List<Hotel>> getHotels();
}
