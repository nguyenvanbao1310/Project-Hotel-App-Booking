package com.example.demo.api.entity;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;


@Entity
@Data
public class BookingSchedule {
    @Id
    private String idBookRoom;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account accountBook;

    @OneToOne
    @JoinColumn(name = "booking_order_id", referencedColumnName = "idOrder")
    private BookingOrder bookingOrder;

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room roomBook;

    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;
}
