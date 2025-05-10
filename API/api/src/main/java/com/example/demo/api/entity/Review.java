package com.example.demo.api.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

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


    private double rating;
    private int rateLocation;
    private int rateService;
    private String content;
    private LocalDate reviewDate;

    @ElementCollection
    private List<String> imageReviews;
}
