package com.hotel.HotelService.controller;
import com.hotel.HotelService.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AdminController {
    @Autowired
    private AdminService adminService;
}
