package com.example.demo.api.repository;

import com.example.demo.api.dto.BookingOrderDTO;
import com.example.demo.api.entity.BookingOrder;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingOrderRepository extends CrudRepository<BookingOrder, String> {

    @Query("SELECT new com.example.demo.api.dto.BookingOrderDTO(bo.idOrder, bo.accountOrder.id, bo.roomOrder.id, bo.hotelOrder.id, bo.dateStart, bo.dateEnd, bo.status, bo.totalPrice, " +
            "new com.example.demo.api.dto.HotelOrderDTO(h.id, h.name, h.address, h.city, h.description, h.hotel_image_url)) " +
            "FROM BookingOrder bo JOIN bo.hotelOrder h " +
            "WHERE bo.accountOrder.id = :accountId")
    List<BookingOrderDTO> findBookingOrdersWithHotelByAccountId(String accountId);

    @Query("SELECT new com.example.demo.api.dto.BookingOrderDTO(bo.idOrder, bo.accountOrder.id, bo.roomOrder.id, bo.hotelOrder.id, bo.dateStart, bo.dateEnd, bo.status, bo.totalPrice, " +
            "new com.example.demo.api.dto.HotelOrderDTO(h.id, h.name, h.address, h.city, h.description, h.hotel_image_url)) " +
            "FROM BookingOrder bo JOIN bo.hotelOrder h " +
            "WHERE bo.accountOrder.id = :accountId AND bo.status = true")
    List<BookingOrderDTO> findCompletedBookingOrdersByAccountId(String accountId);

    @Query("SELECT new com.example.demo.api.dto.BookingOrderDTO(bo.idOrder, bo.accountOrder.id, bo.roomOrder.id, bo.hotelOrder.id, bo.dateStart, bo.dateEnd, bo.status, bo.totalPrice, " +
            "new com.example.demo.api.dto.HotelOrderDTO(h.id, h.name, h.address, h.city, h.description, h.hotel_image_url)) " +
            "FROM BookingOrder bo JOIN bo.hotelOrder h " +
            "WHERE bo.accountOrder.id = :accountId AND bo.status = false")
    List<BookingOrderDTO> findCancelledBookingOrdersByAccountId(String accountId);

    BookingOrder findBookingOrderByIdOrder(@PathVariable String orderId);

    @Modifying
    @Query("UPDATE BookingOrder bo SET bo.status = :status WHERE bo.idOrder = :orderId")
    void updateBookingOrderStatus(String orderId, boolean status);
    Optional<BookingOrder> findTopByOrderByIdOrderDesc();
}
