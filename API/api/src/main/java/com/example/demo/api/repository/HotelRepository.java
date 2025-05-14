package com.example.demo.api.repository;

import com.example.demo.api.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, String> {

    @Query("SELECT h FROM Hotel h JOIN h.rooms r WHERE h.rooms IS NOT EMPTY AND r.priceByDay BETWEEN :priceMin AND :priceMax AND r.priceByDay IS NOT NULL AND r.priceByDay > 0")
    List<Hotel> findHotelsByRoomPriceRange(@Param("priceMin") double priceMin, @Param("priceMax") double priceMax);

    @Query("SELECT h FROM Hotel h " +
            "JOIN h.rooms r " +
            "LEFT JOIN FETCH h.reviews " +
            "WHERE h.rooms IS NOT EMPTY " +
            "AND r.priceByDay BETWEEN :priceMin AND :priceMax " +
            "AND r.priceByDay IS NOT NULL " +
            "AND r.priceByDay > 0 " +
            "AND (SELECT AVG(rw.rating) FROM Review rw WHERE rw.hotelReview = h) >= :rating")
    List<Hotel> findHotelsByPriceRangeAndRating(@Param("priceMin") double priceMin,
                                                @Param("priceMax") double priceMax,
                                                @Param("rating") double rating);

    @Query("SELECT h FROM Hotel h WHERE LOWER(h.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(h.address) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Hotel> searchHotelsByKeyword(@Param("keyword") String keyword);

}
