package com.hotel.HotelService.controller;

import com.hotel.HotelService.model.Room;
import com.hotel.HotelService.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController{
    @Autowired
    private AdminService adminService;

    @PostMapping("/rooms")
    public ResponseEntity<String> addRoom(@RequestBody Room room) {
        adminService.addRoom(room);
        return ResponseEntity.ok("Стаята е добавена успешно");
    }

    @PutMapping("/rooms/{roomId}/deactivate")
    public ResponseEntity<String> deactivateRoom(@PathVariable int roomId) {
        adminService.deactivateRoom(roomId);
        return ResponseEntity.ok("Стаята е деактивирана успешно");
    }

    @PutMapping("/rooms/{roomId}/reactivate")
    public ResponseEntity<String> reactivateRoom(@PathVariable int roomId) {
        adminService.reactivateRoom(roomId);
        return ResponseEntity.ok("Стаята е реактивирана успешно");
    }

    @PostMapping("/rooms/{roomId}/benefits")
    public ResponseEntity<String> addBenefit(@PathVariable int roomId, @RequestParam String benefit) {
        adminService.addBenefit(roomId, benefit);
        return ResponseEntity.ok("Удобството е добавено успешно");
    }

    @DeleteMapping("/rooms/{roomId}/benefits")
    public ResponseEntity<String> deleteBenefit(@PathVariable int roomId, @RequestParam String benefit) {
        adminService.deleteBenefit(roomId, benefit);
        return ResponseEntity.ok("Удобството е премахнато успешно");
    }

    @PutMapping("/rooms/{roomId}/price")
    public ResponseEntity<String> changePrice(@PathVariable int roomId, @RequestParam double price) {
        adminService.changePrice(roomId, price);
        return ResponseEntity.ok("Цената е променена успешно");
    }
}