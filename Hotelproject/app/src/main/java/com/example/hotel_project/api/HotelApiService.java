package com.example.hotel_project.api;

import com.example.hotel_project.model.Hotel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HotelApiService {
    @GET("/api/hotels")
    Call<List<Hotel>> getHotels();

    @GET("/api/hotels/{hotel_id}")
    Call<Hotel> getHotelById(@Path("hotel_id") String hotelId);

    @GET("api/hotels/hotelfilters")
    Call<List<Hotel>> findHotelsByPriceRangeAndRating(
            @Query("priceMin") double priceMin,
            @Query("priceMax") double priceMax,
            @Query("rating") float rating
    );

    @GET("api/hotels/search")
    Call<List<Hotel>> searchHotels(@Query("keyword") String keyword);
}
