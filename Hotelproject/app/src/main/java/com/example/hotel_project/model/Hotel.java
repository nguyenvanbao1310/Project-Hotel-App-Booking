package com.example.hotel_project.model;

import java.io.Serializable;
import java.util.List;

public class Hotel implements Serializable {
    private String id;
    private String name;
    private String address;
    private String city;
    private String description;
    private String hotel_image_url;
    private String hotelAdminId;

    private List<String> hotels_images;

    public Hotel() {
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

    public String getHotelAdminId() {
        return hotelAdminId;
    }

    public void setHotelAdminId(String hotelAdminId) {
        this.hotelAdminId = hotelAdminId;
    }

    public List<String> getHotels_images() {
        return hotels_images;
    }

    public void setHotels_images(List<String> hotels_images) {
        this.hotels_images = hotels_images;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", images=" + hotels_images +
                // Thêm các trường khác nếu có
                '}';
    }

}
