package com.example.demo.api.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReviewDTO {
    private String idReview;
    private String accountId;
    private String userName;
    private String hotelId;
    private int rating;
    private String content;
    private LocalDate reviewDate;
}
