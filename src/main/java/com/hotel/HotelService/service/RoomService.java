package com.hotel.HotelService.service;
import com.hotel.HotelService.model.Room;
import com.hotel.HotelService.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
            if (roomRepository.existsById(room.getId())){
                return true;
            }
        return false;
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

//    public boolean isRoomAvailable(int roomId, LocalDate checkIn, LocalDate checkOut) {
//        Room room = findRoomById(roomId);
//        return !room.getBookingStatus();
//    }

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

    // Find available rooms by type
  //  public List<Room> findAvailableRoomsByType(RoomType roomType) {
    //     return roomRepository.findByRoomTypeAndBookingStatus(roomType, false);
    //}





}
