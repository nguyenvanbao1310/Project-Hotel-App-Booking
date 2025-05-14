package com.example.demo.api.service;

import com.example.demo.api.dto.BookingScheduleDTO;
import com.example.demo.api.dto.HotelBookingDTO;
import com.example.demo.api.dto.RoomDTO;
import com.example.demo.api.entity.BookingSchedule;
import com.example.demo.api.entity.Hotel;
import com.example.demo.api.repository.BookingScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingScheduleService {

    @Autowired
    private BookingScheduleRepository bookingScheduleRepository;

    // Phương thức để lấy tất cả BookingSchedule của account_id
    public List<BookingScheduleDTO> getBookingSchedulesByAccountId(String accountId) {
        List<BookingSchedule> bookingSchedules = bookingScheduleRepository.findByAccountBook_Id(accountId);

        return bookingSchedules.stream().map(bookingSchedule -> {
            BookingScheduleDTO dto = new BookingScheduleDTO();
            dto.setIdBookRoom(bookingSchedule.getIdBookRoom());
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
}
