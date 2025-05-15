package com.example.hotel_project.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class BookingScheduleDTO implements Serializable {
    private String idBookRoom;
    private LocalDateTime dateStart;

    private String idBookingOrder;

    private LocalDateTime dateEnd;
    private String accountId;
    private RoomDTO room;

    private HotelBookingDTO hotel;


    public BookingScheduleDTO() {
    }

    public BookingScheduleDTO(String idBookRoom, LocalDateTime dateStart, String idBookingOrder, LocalDateTime dateEnd, String accountId, RoomDTO room, HotelBookingDTO hotel) {
        this.idBookRoom = idBookRoom;
        this.dateStart = dateStart;
        this.idBookingOrder = idBookingOrder;
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

    public String getIdBookingOrder() {
        return idBookingOrder;
    }

    public void setIdBookingOrder(String idBookingOrder) {
        this.idBookingOrder = idBookingOrder;
    }
}

