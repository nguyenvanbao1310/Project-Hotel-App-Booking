package com.example.demo.api.controller;

import com.example.demo.api.dto.BookingScheduleDTO;
import com.example.demo.api.dto.ResponseObject;
import com.example.demo.api.service.BookingScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/bookingschedules")
public class BookingScheduleController {

    @Autowired
    private BookingScheduleService bookingScheduleService;

    @GetMapping("/{accountId}")
    public List<BookingScheduleDTO> getBookingSchedulesByAccountId(@PathVariable String accountId) {
        return bookingScheduleService.getBookingSchedulesByAccountId(accountId);
    }

    @GetMapping("/rooms/{roomId}")
    public List<BookingScheduleDTO> getBookingSchedulesByRoomId(@PathVariable String roomId) {
        return bookingScheduleService.getBookingSchedulesByRoomId(roomId);
    }

    @PostMapping("/create_booking")
    public ResponseEntity<Boolean> createBooking(
            @RequestParam String accountId,
            @RequestParam String roomId,
            @RequestParam String hotelId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateStart,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateEnd,
            @RequestParam BigDecimal totalPrice
    ) {
        try {
            bookingScheduleService.createBookingSchedule(
                    accountId, roomId, hotelId, dateStart, dateEnd, totalPrice
            );
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }

}
