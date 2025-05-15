package com.example.demo.api.controller;

import com.example.demo.api.dto.BookingOrderDTO;
import com.example.demo.api.service.BookingOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/booking-orders")
public class BookingOrderController {

    @Autowired
    private BookingOrderService bookingOrderService;

    // API để lấy đơn đặt phòng theo accountId
    @GetMapping("/{accountId}")
    public List<BookingOrderDTO> getBookingOrders(@PathVariable String accountId) {
        return bookingOrderService.getBookingOrdersByAccountId(accountId);
    }

    @GetMapping("/booking-complete/{accountId}")
    public List<BookingOrderDTO> getCompleteBookingOrders(@PathVariable String accountId) {
        return bookingOrderService.getCompleteBookingOrdersByAccountId(accountId);
    }

    @GetMapping("/booking-cancelled/{accountId}")
    public List<BookingOrderDTO> getCancelledBookingOrders(@PathVariable String accountId) {
        return bookingOrderService.getCancelledBookingOrdersByAccountId(accountId);
    }

    @GetMapping("/order/{orderId}")
    public BookingOrderDTO getBookingOrderById(@PathVariable String orderId) {
        return bookingOrderService.getBookingOrderById(orderId);
    }

}
