package com.example.demo.api.repository;

import com.example.demo.api.entity.BookingSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingScheduleRepository extends JpaRepository<BookingSchedule, String> {
    List<BookingSchedule> findByAccountBook_IdAndDateEndAfterAndBookingOrder_StatusTrue(
            String accountId,
            LocalDateTime now
    );

    List<BookingSchedule> findByRoomBook_IdAndBookingOrder_StatusTrue(String roomId);
    Optional<BookingSchedule> findTopByOrderByIdBookRoomDesc();
}
