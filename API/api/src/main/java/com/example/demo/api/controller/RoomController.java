package com.example.demo.api.controller;


import com.example.demo.api.dto.RoomDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.api.service.RoomService;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping
    public List<RoomDTO> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/{hotelId}")
    public List<RoomDTO> getAllRoomsByHotelId(@PathVariable String hotelId) {
        return roomService.getAllRoomsByHotelId(hotelId);
    }
}
