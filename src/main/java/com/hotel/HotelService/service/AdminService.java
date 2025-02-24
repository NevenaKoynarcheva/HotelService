package com.hotel.HotelService.service;

import com.hotel.HotelService.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private AdminService adminService;
    @Autowired
    private RoomService roomService;

    public void addRoom(Room room) {
      roomService.addRoom(room);
    }

    public void deactivateRoom(int roomId) {
        roomService.deactivateRoom(roomId);
    }

    public void reactivateRoom(int roomId) {
      roomService.reactivateRoom(roomId);
    }

    public void addBenefit(int roomId, String benefit) {
        roomService.addBenefit(roomId, benefit);
    }


    public void deleteBenefit(int roomId, String benefit) {
        roomService.deleteBenefit(roomId, benefit);
    }

    public void changePrice(int roomId, double price){
      roomService.changePrice(roomId, price);
    }

    private boolean existRoomById(int roomId) {
        return roomService.existRoomById(roomId);
    }

}

