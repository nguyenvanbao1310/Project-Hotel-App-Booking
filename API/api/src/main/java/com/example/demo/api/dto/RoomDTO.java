package com.example.demo.api.dto;
import lombok.Data;

import java.util.List;

@Data
public class RoomDTO {
    private String id;
    private String roomType;
    private int bedNumber;
    private int bathNumber;
    private double priceByHour;
    private double priceByDay;
    private List<String> images;
    private List<String> extension;
}