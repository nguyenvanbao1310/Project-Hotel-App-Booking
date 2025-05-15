package com.example.hotel_project.api;

import com.example.hotel_project.model.BookingScheduleDTO;

import java.math.BigDecimal;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BookingScheduleService {
    @GET("api/bookingschedules/{accountId}")
    Call<List<BookingScheduleDTO>> getBookingSchedules(@Path("accountId") String accountId);

    @GET("api/bookingschedules/rooms/{roomId}")
    Call<List<BookingScheduleDTO>> getBookingSchedulesByRoomId(@Path("roomId") String roomId);

    @POST("api/bookingschedules/create_booking")
    @FormUrlEncoded
    Call<Boolean> createBooking(
            @Field("accountId") String accountId,
            @Field("roomId") String roomId,
            @Field("hotelId") String hotelId,
            @Field("dateStart") String dateStart,
            @Field("dateEnd") String dateEnd,
            @Field("totalPrice") BigDecimal totalPrice
    );

}
