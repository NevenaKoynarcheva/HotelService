package com.hotel.HotelService.repository;

import com.hotel.HotelService.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    boolean existsByRoomNumber(int roomNumber);
    List<Room> findByStatus(boolean status);

}
