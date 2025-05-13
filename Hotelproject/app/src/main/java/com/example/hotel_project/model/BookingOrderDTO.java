package com.example.hotel_project.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class BookingOrderDTO implements Serializable {
    private String idOrder;
    private String accountId;
    private String roomId;
    private String hotelId;
    private String dateStart;
    private String dateEnd;
    private boolean status;
    private BigDecimal totalPrice;
    private HotelOrderDTO hotelOrder;

    public BookingOrderDTO() {
    }

    public BookingOrderDTO(String idOrder, String accountId, String roomId, String hotelId, String dateStart, String dateEnd, boolean status, BigDecimal totalPrice, HotelOrderDTO hotelOrder) {
        this.idOrder = idOrder;
        this.accountId = accountId;
        this.roomId = roomId;
        this.hotelId = hotelId;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.status = status;
        this.totalPrice = totalPrice;
        this.hotelOrder = hotelOrder;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public HotelOrderDTO getHotelOrder() {
        return hotelOrder;
    }

    public void setHotelOrder(HotelOrderDTO hotelOrder) {
        this.hotelOrder = hotelOrder;
    }
}
