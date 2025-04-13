package com.example.demo.api.service;


import com.example.demo.api.entity.Room;
import com.example.demo.api.dto.RoomDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.api.repository.RoomRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public List<RoomDTO> getAllRooms() {
        List<Room> rooms = roomRepository.findAll();

        return rooms.stream().map(room -> {
            RoomDTO dto = new RoomDTO();
            dto.setId(room.getId());
            dto.setRoomType(room.getDetailRoom() != null ? room.getDetailRoom().getBedType() : "N/A");
            dto.setPrice(room.getPrice());
            dto.setImages(room.getDetailRoom() != null ? room.getDetailRoom().getImages() : null);
            dto.setExtension(room.getDetailRoom() != null ? room.getDetailRoom().getExtension() : null);
            return dto;
        }).collect(Collectors.toList());
    }
}
