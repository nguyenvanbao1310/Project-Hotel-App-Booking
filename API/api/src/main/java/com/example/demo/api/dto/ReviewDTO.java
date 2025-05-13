package com.example.demo.api.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ReviewDTO {
    private String idReview;
    private String accountId;
    private String userName;
    private String bookingOrderId;
    private String hotelId;
    private double rating;
    private int rateLocation;
    private int rateService;
    private String content;
    private LocalDate reviewDate;
    private List<String> imageReviews;
}
