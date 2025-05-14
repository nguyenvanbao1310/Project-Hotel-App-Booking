package com.example.demo.api.dto;


import lombok.Data;

@Data
public class HotelBookingDTO {

    private String id;
    private String name;
    private String address;
    private String city;
    private String description;
    private String hotel_image_url;

    public HotelBookingDTO(String id, String name, String address, String city, String description, String hotel_image_url) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.description = description;
        this.hotel_image_url = hotel_image_url;
    }
}
