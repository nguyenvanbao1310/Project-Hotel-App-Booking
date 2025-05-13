package com.example.demo.api.service;


import com.example.demo.api.dto.ReviewDTO;
import com.example.demo.api.entity.Account;
import com.example.demo.api.entity.BookingOrder;
import com.example.demo.api.entity.Hotel;
import com.example.demo.api.entity.Review;
import com.example.demo.api.mapper.ReviewMapper;
import com.example.demo.api.repository.AccountRepository;
import com.example.demo.api.repository.BookingOrderRepository;
import com.example.demo.api.repository.HotelRepository;
import com.example.demo.api.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private BookingOrderRepository bookingOrderRepository;

    public List<Review> getAllReviews() {return reviewRepository.findAll();}

    public List<ReviewDTO> getReviewsByHotelId(String hotelId) {
        List<Review> reviews = reviewRepository.findByHotelReview_Id(hotelId);
        return reviews.stream().map(review -> {
            ReviewDTO dto = new ReviewDTO();
            dto.setIdReview(review.getIdReview());
            dto.setAccountId(review.getAccountReview().getId());
            dto.setUserName(review.getAccountReview().getUsername());
            dto.setBookingOrderId(review.getBookingOrder().getIdOrder());
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
            dto.setBookingOrderId(review.getBookingOrder().getIdOrder());
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

    public ReviewDTO createReview(ReviewDTO dto) {
        // Tìm Account, Hotel, BookingOrder từ id
        Account account = accountRepository.findById(dto.getAccountId()).orElseThrow(() -> new RuntimeException("Account not found"));
        Hotel hotel = hotelRepository.findById(dto.getHotelId()).orElseThrow(() -> new RuntimeException("Hotel not found"));
        BookingOrder bookingOrder = bookingOrderRepository.findById(dto.getBookingOrderId()).orElseThrow(() -> new RuntimeException("BookingOrder not found"));

        String newId = generateNewReviewId();
        dto.setIdReview(newId);

        Review review = ReviewMapper.toEntity(dto, account, hotel, bookingOrder);

        Review savedReview = reviewRepository.save(review);

        return ReviewMapper.toDTO(savedReview);
    }

    public String generateNewReviewId() {
        String lastId = reviewRepository.findLastReviewId(); // Ví dụ: "RV007"
        if (lastId == null) {
            return "RV001";
        }

        int num = Integer.parseInt(lastId.substring(2));
        num++;
        return String.format("RV%03d", num);
    }


}
