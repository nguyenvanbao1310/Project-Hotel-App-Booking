package com.example.demo.api.service;

import com.example.demo.api.entity.Hotel;
import com.example.demo.api.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    public List<Hotel> getAllHotels()
    {
        return hotelRepository.findAll();
    }

    public Hotel getHotelById(String hotel_id)
    {
        return hotelRepository.findById(hotel_id)
                .orElseThrow(() -> new RuntimeException("Hotel not found with hotel_id: " + hotel_id));
    }

    public List<Hotel> getHotelsByPriceRange(double priceMin, double priceMax) {
        return hotelRepository.findHotelsByRoomPriceRange(priceMin, priceMax);
    }

    public List<Hotel> findHotelsByPriceRangeAndRating(double priceMin, double priceMax, float rating) {
        return hotelRepository.findHotelsByPriceRangeAndRating(priceMin, priceMax, rating);
    }
}
