package com.example.demo.api.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Hotel {
    @Id
    String id;
    String name;
    String address;
    String city;

    @OneToOne
    @JoinColumn(name = "admin_id", referencedColumnName = "id")
    private Account hotelAdmin;

    @OneToMany(mappedBy = "hotel")
    private List<Room> rooms;

    private String hotel_image_url;

}
