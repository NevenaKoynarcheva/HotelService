package com.hotel.HotelService.controller;
import com.hotel.HotelService.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class RoomController {
    @Autowired
    private RoomService roomService;
}
