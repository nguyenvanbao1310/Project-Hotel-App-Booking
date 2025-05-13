package com.example.hotel_project.model;

import java.io.Serializable;
import java.util.List;

public class RoomDTO implements Serializable {
    private String id;
    private String roomType;

    private int bedNumber;
    private int bathNumber;
    private double priceByHour;
    private double priceByDay;
    private List<String> images;
    private List<String> extension;

    public RoomDTO(String id, String roomType, int bedNumber, int bathNumber, double priceByHour, double priceByDay, List<String> images, List<String> extension) {
        this.id = id;
        this.roomType = roomType;
        this.bedNumber = bedNumber;
        this.bathNumber = bathNumber;
        this.priceByHour = priceByHour;
        this.priceByDay = priceByDay;
        this.images = images;
        this.extension = extension;
    }

    public RoomDTO() {
    }

    // Getters và setters
    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }

    public double getPriceByHour() {
        return priceByHour;
    }

    public void setPriceByHour(double priceByHour) {
        this.priceByHour = priceByHour;
    }

    public double getPriceByDay() {
        return priceByDay;
    }

    public void setPriceByDay(double priceByDay) {
        this.priceByDay = priceByDay;
    }

    public List<String> getImages() { return images; }
    public void setImages(List<String> images) { this.images = images; }

    public List<String> getExtension() { return extension; }
    public void setExtension(List<String> extension) { this.extension = extension; }

    public int getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(int bedNumber) {
        this.bedNumber = bedNumber;
    }

    public int getBathNumber() {
        return bathNumber;
    }

    public void setBathNumber(int bathNumber) {
        this.bathNumber = bathNumber;
    }
}
