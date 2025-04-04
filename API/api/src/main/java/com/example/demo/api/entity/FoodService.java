package com.example.demo.api.entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class FoodService {
    @Id
    private String idFoodService;

    private double priceService;

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room roomService;

    @OneToOne
    @JoinColumn(name = "menu_id", referencedColumnName = "idMenu")
    private Menu menuService;
}
