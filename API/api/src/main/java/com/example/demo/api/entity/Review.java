package com.example.demo.api.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class Review {
    @Id
    private String idReview;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account accountReview;


    @ManyToOne
    @JoinColumn(name = "hotel_id", referencedColumnName = "id")
    private Hotel hotelReview;

    @ManyToOne
    @JoinColumn(name = "booking_order_id")
    private BookingOrder bookingOrder;

    private int rating;
    private String content;
    private LocalDate reviewDate;
}
