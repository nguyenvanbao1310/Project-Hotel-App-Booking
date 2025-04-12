package com.example.demo.api.entity;

import com.example.demo.api.enums.EnumRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Account {
    @Id
    private String id;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    private EnumRole role;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;

    @OneToMany(mappedBy = "hotelAdmin")
    private List<Hotel> hotel;
}
