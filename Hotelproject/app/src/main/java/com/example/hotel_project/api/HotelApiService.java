package com.example.hotel_project.api;

import com.example.hotel_project.model.Hotel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HotelApiService {
    @GET("/api/hotels")
    Call<List<Hotel>> getHotels();

    @GET("/api/hotels/{hotel_id}")
    Call<Hotel> getHotelById(@Path("hotel_id") String hotelId);
}
