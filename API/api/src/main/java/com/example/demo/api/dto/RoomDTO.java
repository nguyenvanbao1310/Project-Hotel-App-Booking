package com.example.demo.api.dto;
import lombok.Data;

import java.util.List;

@Data
public class RoomDTO {
    private String id;
    private String roomType; // bạn sẽ lấy từ detailRoom
    private double price;
    private List<String> images;
    private List<String> extension;
}