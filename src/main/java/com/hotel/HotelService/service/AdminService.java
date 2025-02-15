package com.hotel.HotelService.service;

import com.hotel.HotelService.model.Room;
import com.hotel.HotelService.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private RoomRepository roomRepository;
    public void addRoom(Room room){
    };
    public void deleteRoom(int roomId){};
    public void addBenefit(int roomId, String benefit){};
    public void deleteBenefit(int roomId, String benefit){};

}
