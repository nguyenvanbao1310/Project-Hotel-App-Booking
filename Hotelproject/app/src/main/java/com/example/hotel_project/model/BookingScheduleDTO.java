package com.example.hotel_project.model;

import java.time.LocalDateTime;

public class BookingScheduleDTO {
    private String idBookRoom;
    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;
    private String accountId;
    private RoomDTO room;

    private HotelBookingDTO hotel;


    public BookingScheduleDTO() {
    }

    public BookingScheduleDTO(String idBookRoom, LocalDateTime dateStart, LocalDateTime dateEnd, String accountId, RoomDTO room, HotelBookingDTO hotel) {
        this.idBookRoom = idBookRoom;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.accountId = accountId;
        this.room = room;
        this.hotel = hotel;
    }

    public String getIdBookRoom() {
        return idBookRoom;
    }

    public void setIdBookRoom(String idBookRoom) {
        this.idBookRoom = idBookRoom;
    }

    public LocalDateTime getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDateTime dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDateTime getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDateTime dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public RoomDTO getRoom() {
        return room;
    }

    public void setRoom(RoomDTO room) {
        this.room = room;
    }

    public HotelBookingDTO getHotel() {
        return hotel;
    }

    public void setHotel(HotelBookingDTO hotel) {
        this.hotel = hotel;
    }
}

