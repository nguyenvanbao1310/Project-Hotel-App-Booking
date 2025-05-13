package com.example.hotel_project.api;

import com.example.hotel_project.model.ReviewDTO;
import com.example.hotel_project.model.RoomDTO;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;


public interface ReviewApiService {
    @GET("api/reviews/hotel/{hotel_id}") // Đặt đúng path của API bên bạn
    Call<List<ReviewDTO>> getReviewByHotelId(@Path("hotel_id") String hotelId);

    @GET("api/reviews/{account_id}")
    Call<List<ReviewDTO>> getReviewByAccountId(@Path("account_id") String accountId);

    @POST("/api/reviews/add_review")  // Địa chỉ API của backend
    Call<ReviewDTO> addReview(@Body ReviewDTO reviewDTO);

    @Multipart
    @POST("api/reviews/uploadImages")
    Call<List<String>> uploadReviewWithImages(
            @Part List<MultipartBody.Part> files
    );
}
