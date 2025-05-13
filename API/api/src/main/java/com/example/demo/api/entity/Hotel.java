package com.example.demo.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @ManyToOne
    @JoinColumn(name = "admin_id", referencedColumnName = "id")
    @JsonIgnore
    private Account hotelAdmin;

    @OneToMany(mappedBy = "hotel")
    private List<Room> rooms;

    @OneToMany(mappedBy = "hotelReview", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Review> reviews;

    private String description;

    private String hotel_image_url;

    @ElementCollection
    private List<String> hotels_images;

    public String getHotelAdminId(){
        return hotelAdmin != null ? hotelAdmin.getId() : null;
    }

}
