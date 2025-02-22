package com.hotel.HotelService.service;
import com.hotel.HotelService.model.Room;
import com.hotel.HotelService.model.RoomType;
import com.hotel.HotelService.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;
    public void saveRoom(Room room){
        roomRepository.save(room);
    }
    public List<Room> findAll(){
        return roomRepository.findAll();

    }
    public boolean isExist(Room room){
        return roomRepository.existsById(room.getId());
    }
    public Room findRoomById(int id){
        List<Room> rooms = findAll();
        int index = -1;
        for (Room room : rooms){
            if (room.getId()==id){
                index = rooms.indexOf(room);
            }
        }

        if (index != -1){
            return rooms.get(index);
        }else {
            return null;
        }
    }


    public void deleteRoom(int id){
        if (findRoomById(id)!=null){
            roomRepository.deleteById(id);
        }else{
            throw new IllegalArgumentException("Липсва такава стая");
        }

    }

    public boolean isRoomAvailable(int roomId, LocalDate checkIn, LocalDate checkOut) {
        Room room = findRoomById(roomId);
        if (!room.getBookingStatus()) {
            return true;
        }

        LocalDate existingCheckIn = room.getCheckIn();
        LocalDate existingCheckOut = room.getCheckOut();
        return checkIn.isAfter(existingCheckOut) || checkOut.isBefore(existingCheckIn);
    }

    public Room checkIn(int roomId, LocalDate checkInDate) {
        Room room = findRoomById(roomId);
        if (room.getBookingStatus()) {
            throw new RuntimeException("Стаята е заета");
        }
        room.setCheckIn(checkInDate);
        room.setBookingStatus(true);
        return roomRepository.save(room);
    }

    public Room checkOut(int roomId, LocalDate checkOutDate) {
        Room room = findRoomById(roomId);
        if (!room.getBookingStatus()) {
            throw new RuntimeException("Стаята е свободна");
        }
        room.setCheckOut(checkOutDate);
        room.setBookingStatus(false);
        return roomRepository.save(room);
    }


    public List<Room> findAvailableRoomsByType(RoomType roomType, LocalDate checkIn, LocalDate checkOut) {
         List<Room> rooms = findAll();
         List<Room> roomsByType = new ArrayList<>();
         for (Room room : rooms){
             if (room.getRoomType().equals(roomType)) {
                 roomsByType.add(room);
             }
         }

         rooms.clear();

         for (Room r : roomsByType){
             if (isRoomAvailable(r.getId(),checkIn,checkOut)){
                 rooms.add(r);
             }
         }

         return rooms;
    }





}

//can be used : public List<Room> findAvailableRoomsByType(RoomType roomType, LocalDate checkIn, LocalDate checkOut) {
//    // Fetch rooms of the specified type directly from the repository
//    List<Room> roomsByType = roomRepository.findByRoomType(roomType);
//
//    // Filter rooms by availability
//    List<Room> availableRooms = new ArrayList<>();
//    for (Room room : roomsByType) {
//        if (isRoomAvailable(room.getId(), checkIn, checkOut)) {
//            availableRooms.add(room);
//        }
//    }
//
//    return availableRooms;
//}public interface RoomRepository extends JpaRepository<Room, Integer> {
//    List<Room> findByRoomType(RoomType roomType); // Add this method
//}