package com.hotel.HotelService.service;
import com.hotel.HotelService.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

}
