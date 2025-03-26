package com.hotel.HotelService.controller;

import com.hotel.HotelService.model.Room;
import com.hotel.HotelService.model.RoomType;
import com.hotel.HotelService.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/login")
    public ResponseEntity<String> logIn(
            @RequestParam String username,
            @RequestParam String password) {
        customerService.logIn(username, password);
        return ResponseEntity.ok("Добре дошли " + username);
    }

    @PostMapping("/register")
    public ResponseEntity<String> saveUser(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String email){
        customerService.saveUser(username, password, email);
        return ResponseEntity.ok("Регистрацията е успешна");
    }

    @GetMapping("/available-rooms")
    public ResponseEntity<List<Room>> findAvailableRoom(
            @RequestParam RoomType roomType,
            @RequestParam LocalDate checkIn,
            @RequestParam LocalDate checkOut) {
        List<Room> availableRooms = customerService.findAvailableRoom(roomType, checkIn, checkOut);
        return ResponseEntity.ok(availableRooms);
    }

}
