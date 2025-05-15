package com.example.hotel_project.api;

import com.example.hotel_project.model.BookingScheduleDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BookingScheduleService {
    @GET("api/bookingschedules/{accountId}")
    Call<List<BookingScheduleDTO>> getBookingSchedules(@Path("accountId") String accountId);

    @GET("api/bookingschedules/rooms/{roomId}")
    Call<List<BookingScheduleDTO>> getBookingSchedulesByRoomId(@Path("roomId") String roomId);

}
