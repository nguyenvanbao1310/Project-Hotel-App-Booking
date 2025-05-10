package com.example.demo.api.repository;


import com.example.demo.api.entity.Guest;
import com.example.demo.api.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {

    List<Review> findByHotelReview_Id(String hotelId);

    List<Review> findByAccountReview_Id(String account_id);
}
