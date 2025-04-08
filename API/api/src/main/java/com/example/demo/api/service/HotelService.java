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
}
