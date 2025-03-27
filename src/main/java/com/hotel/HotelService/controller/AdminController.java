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
    public ResponseEntity<String> addRoom(@RequestBody Room room, @RequestParam int adminId) {
        adminService.addRoom(room, adminId);
        return ResponseEntity.ok("Стаята е добавена успешно");
    }

    @PutMapping("/rooms/deactivate")
    public ResponseEntity<String> deactivateRoom(@RequestParam int roomId) {
        adminService.deactivateRoom(roomId);
        return ResponseEntity.ok("Стаята е деактивирана успешно");
    }

    @PutMapping("/rooms/reactivate")
    public ResponseEntity<String> reactivateRoom(@RequestParam int roomId) {
        adminService.reactivateRoom(roomId);
        return ResponseEntity.ok("Стаята е реактивирана успешно");
    }

    @PostMapping("/rooms/{roomId}/benefits")
    public ResponseEntity<String> addBenefit(@PathVariable int roomId, @RequestParam String benefit) {
        adminService.addBenefit(roomId, benefit);
        return ResponseEntity.ok("Удобството е добавено успешно");
    }

    @DeleteMapping("/rooms/benefits")
    public ResponseEntity<String> deleteBenefit(@RequestParam int roomId, @RequestParam String benefit) {
        adminService.deleteBenefit(roomId, benefit);
        return ResponseEntity.ok("Удобството е премахнато успешно");
    }

    @PutMapping("/rooms/price")
    public ResponseEntity<String> changePrice(@RequestParam int roomId, @RequestParam double price) {
        adminService.changePrice(roomId, price);
        return ResponseEntity.ok("Цената е променена успешно");
    }

    @PutMapping("/hotel/name")
    public ResponseEntity<String> changeHotelName(@RequestParam String name, @RequestParam int id, @RequestParam int adminId) {
        return ResponseEntity.ok(adminService.changeName(name, id, adminId));
    }
    @PutMapping("/hotel/description")
    public ResponseEntity<String> changeHotelDescription(@RequestParam String description, @RequestParam int id, @RequestParam int adminId) {
        return ResponseEntity.ok(adminService.changeDescription(description, id, adminId));
    }
}