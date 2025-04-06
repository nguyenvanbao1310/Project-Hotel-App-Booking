package com.example.demo.api.entity;

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

    private double price;
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "hotel_id", referencedColumnName = "id")
    private Hotel hotel;

    @OneToMany(mappedBy = "roomBook")
    private List<BookingSchedule> bookingSchedules;

    @OneToMany(mappedBy = "roomOrder")
    private List<BookingOrder> bookingOrders;
}
