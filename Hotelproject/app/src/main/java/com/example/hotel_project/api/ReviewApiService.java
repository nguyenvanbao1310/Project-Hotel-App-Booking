package com.example.hotel_project.api;

import com.example.hotel_project.model.ReviewDTO;
import com.example.hotel_project.model.RoomDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface ReviewApiService {
    @GET("api/reviews/hotel/{hotel_id}") // Đặt đúng path của API bên bạn
    Call<List<ReviewDTO>> getReviewByHotelId(@Path("hotel_id") String hotelId);

    @GET("api/reviews/{account_id}")
    Call<List<ReviewDTO>> getReviewByAccountId(@Path("account_id") String accountId);
}
