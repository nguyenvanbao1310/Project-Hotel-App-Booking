package com.example.hotel_project.api;
import com.example.hotel_project.model.RoomDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import java.util.List;

public interface RoomApiService {
    @GET("api/rooms") // Đặt đúng path của API bên bạn
    Call<List<RoomDTO>> getAllRooms();
}
