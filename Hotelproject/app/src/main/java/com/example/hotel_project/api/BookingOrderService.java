package com.example.hotel_project.api;

import com.example.hotel_project.model.BookingOrderDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BookingOrderService {
    @GET("api/booking-orders/{accountId}")
    Call<List<BookingOrderDTO>> getBookingOrdersByAccountId(@Path("accountId") String accountId);
}
