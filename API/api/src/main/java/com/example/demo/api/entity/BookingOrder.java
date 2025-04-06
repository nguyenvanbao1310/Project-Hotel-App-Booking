package com.example.demo.api.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Data
public class BookingOrder {
    @Id
    private String idOrder;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account accountOrder;

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room roomOrder;

    @OneToMany(mappedBy = "bookingOrder")
    private List<Review> reviews;

    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;
    private boolean status;
}
