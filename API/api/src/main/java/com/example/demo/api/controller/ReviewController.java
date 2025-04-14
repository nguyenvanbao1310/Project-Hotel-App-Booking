package com.example.demo.api.controller;


import com.example.demo.api.dto.ReviewDTO;
import com.example.demo.api.entity.Review;
import com.example.demo.api.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping()
    public List<Review> getReviews() {return reviewService.getAllReviews();}

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByHotel(@PathVariable String hotelId) {
        return ResponseEntity.ok(reviewService.getReviewsByHotelId(hotelId));
    }
}
