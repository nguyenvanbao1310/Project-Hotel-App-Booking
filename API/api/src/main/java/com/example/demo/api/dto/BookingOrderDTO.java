package com.example.demo.api.dto;

import com.example.demo.api.entity.Hotel;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class BookingOrderDTO {
    private String idOrder;
    private String accountId;
    private String roomId;
    private String hotelId;
    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;
    private boolean status;
    private BigDecimal totalPrice;
    private HotelOrderDTO hotelOrder;

    public BookingOrderDTO(String idOrder, String accountId, String roomId, String hotelId,
                           LocalDateTime dateStart, LocalDateTime dateEnd, boolean status,
                           BigDecimal totalPrice, HotelOrderDTO hotelOrder) {
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
}
