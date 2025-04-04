package com.example.demo.api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

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
}
