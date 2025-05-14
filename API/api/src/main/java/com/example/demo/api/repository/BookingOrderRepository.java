package com.example.demo.api.repository;

import com.example.demo.api.dto.BookingOrderDTO;
import com.example.demo.api.entity.BookingOrder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

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

}
