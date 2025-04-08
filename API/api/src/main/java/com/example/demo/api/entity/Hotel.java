package com.example.demo.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Account hotelAdmin;

    @OneToMany(mappedBy = "hotel")
    private List<Room> rooms;

    private String description;

    private String hotel_image_url;

    public String getHotelAdminId(){
        return hotelAdmin != null ? hotelAdmin.getId() : null;
    }

}
