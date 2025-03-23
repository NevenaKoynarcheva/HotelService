package com.hotel.HotelService.controller;

import com.hotel.HotelService.model.Hotel;
import com.hotel.HotelService.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home")
public class HotelController {
    @Autowired
    private HotelService hotelService;
    @GetMapping("/hotel")
    public ResponseEntity<String> getName(@RequestParam int id){
        return ResponseEntity.ok(hotelService.getHotel(id));
    }

    @GetMapping("/description")
    public ResponseEntity<String> getDescription(@RequestParam int id){
        return ResponseEntity.ok(hotelService.getDescription(id));
    }
    @PostMapping("/IT")
    public ResponseEntity<String> createHotel(@RequestParam String name, @RequestParam String description){
        hotelService.save(name, description);
        return ResponseEntity.ok("Създаден хотел");
    }
}
