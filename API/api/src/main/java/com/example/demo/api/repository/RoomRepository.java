package com.example.demo.api.repository;

import com.example.demo.api.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, String>
{
    List<Room> findByHotel_Id(String hotelId);

    Room findRoomById(String id);
}
