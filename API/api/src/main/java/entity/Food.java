package entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Food {
    @Id
    private String idFood;

    private String nameFood;
    private double priceFood;

    @ManyToOne
    @JoinColumn(name = "menu_id", referencedColumnName = "idMenu")
    private Menu menu;
}

