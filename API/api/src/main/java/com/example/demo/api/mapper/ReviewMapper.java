package com.example.demo.api.mapper;

import com.example.demo.api.dto.ReviewDTO;
import com.example.demo.api.entity.Account;
import com.example.demo.api.entity.BookingOrder;
import com.example.demo.api.entity.Hotel;
import com.example.demo.api.entity.Review;

public class ReviewMapper {
    public static Review toEntity(ReviewDTO dto, Account account, Hotel hotel, BookingOrder bookingOrder) {
        Review review = new Review();
        review.setIdReview(dto.getIdReview());
        review.setRating(dto.getRating());
        review.setRateLocation(dto.getRateLocation());
        review.setRateService(dto.getRateService());
        review.setContent(dto.getContent());
        review.setReviewDate(dto.getReviewDate());
        review.setImageReviews(dto.getImageReviews());
        
        // Gán các đối tượng liên quan
        review.setAccountReview(account);
        review.setHotelReview(hotel);
        review.setBookingOrder(bookingOrder);

        return review;
    }

    public static ReviewDTO toDTO(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setIdReview(review.getIdReview());
        dto.setAccountId(review.getAccountReview().getId());
        dto.setUserName(review.getAccountReview().getUsername());
        dto.setHotelId(review.getHotelReview().getId());
        dto.setRating(review.getRating());
        dto.setRateLocation(review.getRateLocation());
        dto.setRateService(review.getRateService());
        dto.setContent(review.getContent());
        dto.setReviewDate(review.getReviewDate());
        dto.setImageReviews(review.getImageReviews());
        return dto;
    }
}
