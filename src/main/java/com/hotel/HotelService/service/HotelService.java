package com.hotel.HotelService.service;

import com.hotel.HotelService.model.Hotel;
import com.hotel.HotelService.repository.hotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotelService {
    @Autowired
    private hotelRepository hotelRepository;
    String changeName(String name, int id){
        hotelRepository.getReferenceById(id).setName(name);
        return "Името е сменено на " + hotelRepository.getReferenceById(id).getName();
    }

    String changeDescription(String description, int id){
        hotelRepository.getReferenceById(id).setDescription(description);
        return "Описание на хотела " + hotelRepository.getReferenceById(id).getDescription();
    }
    public String getHotel(int id){
        return hotelRepository.getReferenceById(id).getName();
    }
    public String getDescription(int id){
        return hotelRepository.getReferenceById(id).getDescription();
    }
    public void save(String name, String description){
        Hotel hotel = new Hotel();
        hotel.setName(name);
        hotel.setDescription(description);
        hotelRepository.save(hotel);
    }

}
