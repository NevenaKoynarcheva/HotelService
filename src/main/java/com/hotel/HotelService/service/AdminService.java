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
        roomRepository.save(room); //We can have exact same rooms in our hotel

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
                throw new IllegalArgumentException("The room already doesn't contains this benefit");
            }
        }
    }

    private boolean existRoomById(int roomId) {
        if (!roomRepository.existsById(roomId)) {
            throw new IllegalArgumentException("The room doesn't exist");
        }
        return true;
    }
}

