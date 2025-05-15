package com.example.demo.api.service;

import com.example.demo.api.dto.BookingOrderDTO;
import com.example.demo.api.dto.HotelOrderDTO;
import com.example.demo.api.entity.Account;
import com.example.demo.api.entity.BookingOrder;
import com.example.demo.api.entity.Hotel;
import com.example.demo.api.entity.Room;
import com.example.demo.api.repository.BookingOrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingOrderService {
    @Autowired
    private BookingOrderRepository bookingOrderRepository;

    // Phương thức lấy đơn đặt phòng theo accountId
    public List<BookingOrderDTO> getBookingOrdersByAccountId(String accountId) {
        return bookingOrderRepository.findBookingOrdersWithHotelByAccountId(accountId);
    }
    public void updatePaymentStatus(String idOrder, boolean status) {
        bookingOrderRepository.findById(idOrder).ifPresent(bookingOrder -> {
            bookingOrder.setStatus(status);
            bookingOrderRepository.save(bookingOrder);
        });
    }


    public List<BookingOrderDTO> getCompleteBookingOrdersByAccountId(String accountId) {
        return bookingOrderRepository.findCompletedBookingOrdersByAccountId(accountId);
    }

    public List<BookingOrderDTO> getCancelledBookingOrdersByAccountId(String accountId) {
        return bookingOrderRepository.findCancelledBookingOrdersByAccountId(accountId);
    }

    public BookingOrderDTO getBookingOrderById(String orderId) {

        BookingOrder bookingOrder = bookingOrderRepository.findBookingOrderByIdOrder(orderId);
        Hotel hotel = bookingOrder.getHotelOrder();

        HotelOrderDTO hotelOrderDTO = new HotelOrderDTO(
                hotel.getId(),
                hotel.getName(),
                hotel.getAddress(),
                hotel.getCity(),
                hotel.getDescription(),
                hotel.getHotel_image_url()
        );

        return new BookingOrderDTO(
                bookingOrder.getIdOrder(),
                bookingOrder.getAccountOrder().getId(),
                bookingOrder.getRoomOrder().getId(),
                bookingOrder.getHotelOrder().getId(),
                bookingOrder.getDateStart(),
                bookingOrder.getDateEnd(),
                bookingOrder.isStatus(),
                bookingOrder.getTotalPrice(),
                hotelOrderDTO
        );
    }

    @Transactional
    public void changeBookingOrderStatus(String orderId, boolean status) {
        bookingOrderRepository.updateBookingOrderStatus(orderId, status);
    }

    public BookingOrder createBookingOrder(Account account, Room room, Hotel hotel,
                                           LocalDateTime dateStart, LocalDateTime dateEnd,
                                           BigDecimal totalPrice) {
        BookingOrder bookingOrder = new BookingOrder();
        bookingOrder.setIdOrder(generateBookingOrderId());
        bookingOrder.setAccountOrder(account);
        bookingOrder.setRoomOrder(room);
        bookingOrder.setHotelOrder(hotel);
        bookingOrder.setDateStart(dateStart);
        bookingOrder.setDateEnd(dateEnd);
        bookingOrder.setTotalPrice(totalPrice);
        bookingOrder.setStatus(true);
        return bookingOrderRepository.save(bookingOrder);
    }

    public String generateBookingOrderId() {
        String lastId = bookingOrderRepository.findTopByOrderByIdOrderDesc()
                .map(BookingOrder::getIdOrder)
                .orElse("BO000");
        int number = Integer.parseInt(lastId.substring(2)) + 1;
        return String.format("BO%03d", number);
    }


}
