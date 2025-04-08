package com.example.demo.api.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class DetailRoom {
    @Id
    private String id;

    private double roomArea;
    private int bedNumbers;
    private int bathroomNumber;
    private String bedType;

    @ElementCollection
    private List<String> images;

    @ElementCollection
    private List<String> extension;
}
