package com.example.demo.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Room {
    @Id
    private String id;

    @OneToOne
    @JoinColumn(name = "detail_room_id", referencedColumnName = "id")
    private DetailRoom detailRoom;

    private double priceByHour;
    private double priceByDay;

    private boolean status;

    @ManyToOne
    @JoinColumn(name = "hotel_id", referencedColumnName = "id")
    @JsonIgnore
    private Hotel hotel;

    @OneToMany(mappedBy = "roomBook")
    @JsonIgnore
    private List<BookingSchedule> bookingSchedules;

    @OneToMany(mappedBy = "roomOrder")
    @JsonIgnore
    private List<BookingOrder> bookingOrders;

    public String getHotelId() {
        return hotel != null ? hotel.getId() : null;
    }
}

