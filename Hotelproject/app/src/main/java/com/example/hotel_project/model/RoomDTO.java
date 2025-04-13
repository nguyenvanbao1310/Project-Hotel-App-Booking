package com.example.hotel_project.model;

import java.util.List;

public class RoomDTO {
    private String id;
    private String roomType;
    private double price;
    private List<String> images;
    private List<String> extension;

    // Getters và setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public List<String> getImages() { return images; }
    public void setImages(List<String> images) { this.images = images; }

    public List<String> getExtension() { return extension; }
    public void setExtension(List<String> extension) { this.extension = extension; }
}
