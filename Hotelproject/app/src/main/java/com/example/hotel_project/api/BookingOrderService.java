package com.example.hotel_project.api;

import com.example.hotel_project.model.BookingOrderDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BookingOrderService {
    @GET("api/booking-orders/{accountId}")
    Call<List<BookingOrderDTO>> getBookingOrdersByAccountId(@Path("accountId") String accountId);

    @GET("api/booking-orders/booking-complete/{accountId}")
    Call<List<BookingOrderDTO>> getCompleteBookingOrdersByAccountId(@Path("accountId") String accountId);

    @GET("api/booking-orders/booking-cancelled/{accountId}")
    Call<List<BookingOrderDTO>> getCancelledBookingOrdersByAccountId(@Path("accountId") String accountId);
}
