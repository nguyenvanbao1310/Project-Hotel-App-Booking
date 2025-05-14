package com.example.demo.api.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BookingScheduleDTO {
    private String idBookRoom;
    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;
    private String accountId;
    private RoomDTO room;
    private HotelBookingDTO hotel;
}
