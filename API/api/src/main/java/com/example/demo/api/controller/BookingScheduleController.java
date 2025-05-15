package com.example.demo.api.controller;

import com.example.demo.api.dto.BookingScheduleDTO;
import com.example.demo.api.service.BookingScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
