package com.hotel.HotelService.service;

import com.hotel.HotelService.model.Room;
import com.hotel.HotelService.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AdminService {
    @Autowired
    private RoomRepository roomRepository;

    public void addRoom(Room room) {
        if (!roomRepository.existsByRoomNumber(room.getRoomNumber())){
            roomRepository.save(room); //Можем да имаме еднакви стаи в хотела
        }else {
            throw new IllegalArgumentException("Не могат две стаи да са с един и същ номер");
        }
    }



    public void deleteRoom(int roomId) {
        if(existRoomById(roomId)){
            roomRepository.deleteById(roomId);
        }
    }

    public void addBenefit(int roomId, String benefit) {
        if (existRoomById(roomId)) {
            Room room = roomRepository.getReferenceById(roomId);

            List<String> benefits = Arrays.stream(room.getBenefits().split(", ")).toList();
            if (!benefits.contains(benefit)) {
                room.setBenefits(", " + benefit);
            }
        }

    }


    public void deleteBenefit(int roomId, String benefit) {
        if (existRoomById(roomId)) {
            Room room = roomRepository.getReferenceById(roomId);
            List<String> benefits = new ArrayList<>(Arrays.stream(room.getBenefits().split(", ")).toList());
            int index = -1;
            if (benefits.contains(benefit)) {
                index = benefits.indexOf(benefit);
            }

            if (index != -1) {
                benefits.remove(index);
            } else {
                throw new IllegalArgumentException("Стаята не съдържа това удобство");
            }
        }
    }

    public void changePrice(int roomId, double price){
        if (existRoomById(roomId)){
            roomRepository.getReferenceById(roomId).setPrice(price);
        }
    }

    private boolean existRoomById(int roomId) {
        if (!roomRepository.existsById(roomId)) {
            throw new IllegalArgumentException("Стаята не съществува");
        }
        return true;
    }
}

