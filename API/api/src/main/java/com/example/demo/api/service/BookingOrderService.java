package com.example.demo.api.service;

import com.example.demo.api.dto.BookingOrderDTO;
import com.example.demo.api.repository.BookingOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingOrderService {
    @Autowired
    private BookingOrderRepository bookingOrderRepository;

    // Phương thức lấy đơn đặt phòng theo accountId
    public List<BookingOrderDTO> getBookingOrdersByAccountId(String accountId) {
        return bookingOrderRepository.findBookingOrdersWithHotelByAccountId(accountId);
    }

}
