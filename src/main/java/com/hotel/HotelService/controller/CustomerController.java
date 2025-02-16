package com.hotel.HotelService.controller;
import com.hotel.HotelService.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CustomerController {
    @Autowired
    private CustomerService customerService;
}
