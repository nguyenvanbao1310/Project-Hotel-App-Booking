package com.example.hotel_project.model;

import java.io.Serializable;

public class HotelBookingDTO  implements Serializable {
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

    public HotelBookingDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHotel_image_url() {
        return hotel_image_url;
    }

    public void setHotel_image_url(String hotel_image_url) {
        this.hotel_image_url = hotel_image_url;
    }
}
