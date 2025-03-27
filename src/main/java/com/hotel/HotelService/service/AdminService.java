package com.hotel.HotelService.service;

import com.hotel.HotelService.model.Admin;
import com.hotel.HotelService.model.AdminRole;
import com.hotel.HotelService.model.Room;
import com.hotel.HotelService.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private RoomService roomService;
    @Autowired
    private HotelService hotelService;
    private Admin getAdmin(String username) {
        return adminRepository.findByUsername(username).orElse(null);
    }
    public void logIn(String username, String password) {
        if (!adminRepository.existsByUsername(username)) {
            throw new RuntimeException("Некоректни данни");
        } else {
            Admin admin = getAdmin(username);
            if (admin != null) {
                if (!admin.getPassword().equals(password)) {
                    throw new RuntimeException("Некоректни данни");
                }
            }
        }
    }

    public void addRoom(Room room, int adminId) {
        checkAdminRole(adminId, AdminRole.HighLevel);
      roomService.addRoom(room);
    }

    public void deactivateRoom(int roomId) {
        roomService.deactivateRoom(roomId);
    }

    public void reactivateRoom(int roomId) {
      roomService.reactivateRoom(roomId);
    }

    public void addBenefit(int roomId, String benefit) {
        roomService.addBenefit(roomId, benefit);
    }


    public void deleteBenefit(int roomId, String benefit) {
        roomService.deleteBenefit(roomId, benefit);
    }

    public void changePrice(int roomId, double price){
      roomService.changePrice(roomId, price);
    }

    private boolean existRoomById(int roomId) {
        return roomService.existRoomById(roomId);}

    public String changeName(String name, int id, int adminId){
        checkAdminRole(adminId, AdminRole.HighLevel);
        return hotelService.changeName(name, id);
    }

    public String changeDescription(String description, int id, int adminId){
        checkAdminRole(adminId, AdminRole.HighLevel);
        return hotelService.changeDescription(description, id);
    }
    private void checkAdminRole(int adminId, AdminRole... allowedRoles) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Липсва такъв админ"));

        for (AdminRole role : allowedRoles) {
            if (admin.getAdminRole() == role) {
                return;
            }
        }

        throw new RuntimeException("Отказан достъп. Изисква се роля на: " +
                Arrays.toString(allowedRoles));
    }

}

