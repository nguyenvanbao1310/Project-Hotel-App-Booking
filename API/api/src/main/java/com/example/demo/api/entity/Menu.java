package com.example.demo.api.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Menu {
    @Id
    private String idMenu;

    private String nameMenu;
    private String describeMenu;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    private List<Food> listFood;
}
