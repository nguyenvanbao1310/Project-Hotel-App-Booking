package com.example.demo.api.controller;


import com.example.demo.api.entity.Hotel;
import com.example.demo.api.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/hotelfilters")
    public List<Hotel> findHotelsByPriceRangeAndRating(@RequestParam double priceMin, @RequestParam double priceMax, @RequestParam float rating) {
        return hotelService.findHotelsByPriceRangeAndRating(priceMin, priceMax, rating );
    }

    @GetMapping("/search")
    public ResponseEntity<List<Hotel>> searchHotels(@RequestParam String keyword) {
        List<Hotel> hotels = hotelService.searchHotels(keyword);
        return ResponseEntity.ok(hotels);
    }
}
