package com.example.demo.api.service;


import com.example.demo.api.dto.ReviewDTO;
import com.example.demo.api.entity.Review;
import com.example.demo.api.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> getAllReviews() {return reviewRepository.findAll();}

    public List<ReviewDTO> getReviewsByHotelId(String hotelId) {
        List<Review> reviews = reviewRepository.findByHotelReview_Id(hotelId);
        return reviews.stream().map(review -> {
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
        }).collect(Collectors.toList());
    }

    public List<ReviewDTO> getReviewsByAccountId(String account_id) {
        List<Review> reviews = reviewRepository.findByAccountReview_Id(account_id);
        return reviews.stream().map(review -> {
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
        }).collect(Collectors.toList());
    }
}
