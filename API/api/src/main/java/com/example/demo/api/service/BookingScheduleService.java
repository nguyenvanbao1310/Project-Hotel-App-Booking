package com.example.demo.api.service;

import com.example.demo.api.dto.BookingScheduleDTO;
import com.example.demo.api.dto.HotelBookingDTO;
import com.example.demo.api.dto.RoomDTO;
import com.example.demo.api.entity.*;
import com.example.demo.api.repository.AccountRepository;
import com.example.demo.api.repository.BookingScheduleRepository;
import com.example.demo.api.repository.HotelRepository;
import com.example.demo.api.repository.RoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingScheduleService {

    @Autowired
    private BookingScheduleRepository bookingScheduleRepository;

    @Autowired
    private BookingOrderService bookingOrderService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private HotelRepository hotelRepository;




    // Phương thức để lấy tất cả BookingSchedule của account_id
    public List<BookingScheduleDTO> getBookingSchedulesByAccountId(String accountId) {
        List<BookingSchedule> bookingSchedules = bookingScheduleRepository.findByAccountBook_IdAndDateEndAfterAndBookingOrder_StatusTrue(accountId, LocalDateTime.now());

        return bookingSchedules.stream().map(bookingSchedule -> {
            BookingScheduleDTO dto = new BookingScheduleDTO();
            dto.setIdBookRoom(bookingSchedule.getIdBookRoom());
            dto.setIdBookingOrder(bookingSchedule.getBookingOrder().getIdOrder());
            dto.setDateStart(bookingSchedule.getDateStart());
            dto.setDateEnd(bookingSchedule.getDateEnd());
            dto.setAccountId(bookingSchedule.getAccountBook().getId());

            RoomDTO roomDTO = new RoomDTO();
            roomDTO.setId(bookingSchedule.getRoomBook().getId());
            roomDTO.setRoomType(bookingSchedule.getRoomBook().getDetailRoom().getBedType());
            roomDTO.setBedNumber(bookingSchedule.getRoomBook().getDetailRoom().getBedNumbers());
            roomDTO.setBathNumber(bookingSchedule.getRoomBook().getDetailRoom().getBathroomNumber());
            roomDTO.setPriceByHour(bookingSchedule.getRoomBook().getPriceByHour());
            roomDTO.setPriceByDay(bookingSchedule.getRoomBook().getPriceByDay());

            // Giả sử bạn có danh sách hình ảnh và extension trong `Room`
            roomDTO.setImages(bookingSchedule.getRoomBook().getDetailRoom().getImages());
            roomDTO.setExtension(bookingSchedule.getRoomBook().getDetailRoom().getExtension());

            dto.setRoom(roomDTO);


            Hotel hotel = bookingSchedule.getRoomBook().getHotel(); // hoặc getHotel() từ room
            HotelBookingDTO hotelDTO = new HotelBookingDTO(
                    hotel.getId(),
                    hotel.getName(),
                    hotel.getAddress(),
                    hotel.getCity(),
                    hotel.getDescription(),
                    hotel.getHotel_image_url()
            );
            dto.setHotel(hotelDTO);
            return dto;
        }).collect(Collectors.toList());
    }

    public List<BookingScheduleDTO> getBookingSchedulesByRoomId(String roomId) {
        List<BookingSchedule> bookingSchedules = bookingScheduleRepository.findByRoomBook_IdAndBookingOrder_StatusTrue(roomId);
        return bookingSchedules.stream().map(bookingSchedule -> {
            BookingScheduleDTO dto = new BookingScheduleDTO();
            dto.setIdBookRoom(bookingSchedule.getIdBookRoom());
            dto.setIdBookingOrder(bookingSchedule.getBookingOrder().getIdOrder());
            dto.setDateStart(bookingSchedule.getDateStart());
            dto.setDateEnd(bookingSchedule.getDateEnd());
            dto.setAccountId(bookingSchedule.getAccountBook().getId());

            RoomDTO roomDTO = new RoomDTO();
            roomDTO.setId(bookingSchedule.getRoomBook().getId());
            roomDTO.setRoomType(bookingSchedule.getRoomBook().getDetailRoom().getBedType());
            roomDTO.setBedNumber(bookingSchedule.getRoomBook().getDetailRoom().getBedNumbers());
            roomDTO.setBathNumber(bookingSchedule.getRoomBook().getDetailRoom().getBathroomNumber());
            roomDTO.setPriceByHour(bookingSchedule.getRoomBook().getPriceByHour());
            roomDTO.setPriceByDay(bookingSchedule.getRoomBook().getPriceByDay());

            // Giả sử bạn có danh sách hình ảnh và extension trong `Room`
            roomDTO.setImages(bookingSchedule.getRoomBook().getDetailRoom().getImages());
            roomDTO.setExtension(bookingSchedule.getRoomBook().getDetailRoom().getExtension());

            dto.setRoom(roomDTO);


            Hotel hotel = bookingSchedule.getRoomBook().getHotel(); // hoặc getHotel() từ room
            HotelBookingDTO hotelDTO = new HotelBookingDTO(
                    hotel.getId(),
                    hotel.getName(),
                    hotel.getAddress(),
                    hotel.getCity(),
                    hotel.getDescription(),
                    hotel.getHotel_image_url()
            );
            dto.setHotel(hotelDTO);
            return dto;
        }).collect(Collectors.toList());
    }

    @Transactional
    public void createBookingSchedule(String accountId, String roomId, String hotelId,
                              LocalDateTime dateStart, LocalDateTime dateEnd,
                              BigDecimal totalPrice) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        BookingOrder bookingOrder = bookingOrderService.createBookingOrder(
                account, room, hotel, dateStart, dateEnd, totalPrice);

        BookingSchedule bookingSchedule = new BookingSchedule();
        bookingSchedule.setIdBookRoom(generateBookingScheduleId());
        bookingSchedule.setAccountBook(account);
        bookingSchedule.setRoomBook(room);
        bookingSchedule.setBookingOrder(bookingOrder);
        bookingSchedule.setDateStart(dateStart);
        bookingSchedule.setDateEnd(dateEnd);
        bookingScheduleRepository.save(bookingSchedule);
    }

    public String generateBookingScheduleId() {
        String lastId = bookingScheduleRepository.findTopByOrderByIdBookRoomDesc()
                .map(BookingSchedule::getIdBookRoom)
                .orElse("BS000");

        int number = Integer.parseInt(lastId.substring(2)) + 1;
        return String.format("BS%03d", number);
    }
}
