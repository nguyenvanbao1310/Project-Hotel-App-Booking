package com.example.hotel_project.model;

import java.time.LocalDate;
import java.util.List;

public class ReviewDTO {

    private String idReview;
    private String accountId;

    private String userName;

    private String hotelId;
    private int rating;
    private String content;
    private LocalDate reviewDate;

    private List<String> imageReviews;


    public ReviewDTO(String idReview, String accountId, String userName, String hotelId, int rating, String content, LocalDate reviewDate, List<String> imageReviews) {
        this.idReview = idReview;
        this.accountId = accountId;
        this.userName = userName;
        this.hotelId = hotelId;
        this.rating = rating;
        this.content = content;
        this.reviewDate = reviewDate;
        this.imageReviews = imageReviews;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
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
}
