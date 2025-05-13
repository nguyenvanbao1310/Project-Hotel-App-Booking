package com.example.hotel_project.model;

import java.time.LocalDate;
import java.util.List;

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


    public ReviewDTO(String idReview, String accountId, String userName, String bookingOrderId, String hotelId, double rating, int rateLocation, int rateService, String content, LocalDate reviewDate, List<String> imageReviews) {
        this.idReview = idReview;
        this.accountId = accountId;
        this.userName = userName;
        this.bookingOrderId = bookingOrderId;
        this.hotelId = hotelId;
        this.rating = rating;
        this.rateLocation = rateLocation;
        this.rateService = rateService;
        this.content = content;
        this.reviewDate = reviewDate;
        this.imageReviews = imageReviews;
    }

    public ReviewDTO() {
    }

    public String getIdReview() {
        return idReview;
    }

    public void setIdReview(String idReview) {
        this.idReview = idReview;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getRateLocation() {
        return rateLocation;
    }

    public void setRateLocation(int rateLocation) {
        this.rateLocation = rateLocation;
    }

    public int getRateService() {
        return rateService;
    }

    public void setRateService(int rateService) {
        this.rateService = rateService;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }

    public List<String> getImageReviews() {
        return imageReviews;
    }

    public void setImageReviews(List<String> imageReviews) {
        this.imageReviews = imageReviews;
    }

    public String getBookingOrderId() {
        return bookingOrderId;
    }

    public void setBookingOrderId(String bookingOrderId) {
        this.bookingOrderId = bookingOrderId;
    }
}
