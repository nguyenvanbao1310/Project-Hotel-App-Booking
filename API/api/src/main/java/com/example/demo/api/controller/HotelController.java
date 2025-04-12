package com.example.demo.api.controller;


import com.example.demo.api.entity.Hotel;
import com.example.demo.api.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {
    @Autowired
    HotelService hotelService;

    @GetMapping
    public List<Hotel> getHotels() {
        return hotelService.getAllHotels();
    }

    @GetMapping("/{hotel_id}")
    public Hotel getHotelById(@PathVariable String hotel_id) {
        return hotelService.getHotelById(hotel_id);
    }

}
