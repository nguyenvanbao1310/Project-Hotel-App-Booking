package com.example.hotel_project.api;
import com.example.hotel_project.model.RoomDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface RoomApiService {
    @GET("api/rooms") // Đặt đúng path của API bên bạn
    Call<List<RoomDTO>> getAllRooms();

    @GET("api/rooms/{hotelId}")
    Call<List<RoomDTO>> getAllRoomsByHotelId(@Path("hotelId") String hotelId);

    @GET("api/rooms/room/{roomId}")
    Call<RoomDTO> getRoomById(@Path("roomId") String roomId);
}
