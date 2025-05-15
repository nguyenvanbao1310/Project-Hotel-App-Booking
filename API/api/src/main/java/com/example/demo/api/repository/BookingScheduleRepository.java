package com.example.demo.api.repository;

import com.example.demo.api.entity.BookingSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingScheduleRepository extends JpaRepository<BookingSchedule, String> {
    List<BookingSchedule> findByAccountBook_IdAndDateEndAfter(String accountId, LocalDateTime now);

    List<BookingSchedule> findByRoomBook_Id(String roomId);
}
