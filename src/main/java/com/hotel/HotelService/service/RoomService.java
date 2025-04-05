package com.hotel.HotelService.service;
import com.hotel.HotelService.model.Reservation;
import com.hotel.HotelService.model.ReservationStatus;
import com.hotel.HotelService.model.Room;
import com.hotel.HotelService.model.RoomType;
import com.hotel.HotelService.repository.ReservationRepository;
import com.hotel.HotelService.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    public void saveRoom(Room room){
        roomRepository.save(room);
    }
    public List<Room> findAll(){
        return roomRepository.findAll();

    }
    public boolean isExist(Room room){
        return roomRepository.existsById(room.getId());
    }
    public Room findRoomById(int id){
        List<Room> rooms = findAll();
        int index = -1;
        for (Room room : rooms){
            if (room.getId()==id){
                index = rooms.indexOf(room);
            }
        }

        if (index != -1){
            return rooms.get(index);
        }else {
            return null;
        }
    }

    public boolean isRoomAvailable(int roomId, LocalDate checkIn, LocalDate checkOut) {
            Room room = findRoomById(roomId);
            if (room == null || !room.getStatus()) {
                return false;
            }

            List<Reservation> activeReservations
                    = reservationRepository.findByRoomIdAndStatusNot(roomId, ReservationStatus.CANCELLED);

            for (Reservation r : activeReservations) {
                if (datesOverlap(checkIn, checkOut, r.getCheckInDate(), r.getCheckOutDate())) {
                    return false;
                }
            }
            return true;
        }

        private boolean datesOverlap(LocalDate start1, LocalDate end1,
                LocalDate start2, LocalDate end2) {
            return !start1.isAfter(end2) && !end1.isBefore(start2);}


    public List<Room> findAvailableRoomsByType(RoomType roomType, LocalDate checkIn, LocalDate checkOut) {
        List<Room> rooms = findAll();
        List<Room> roomsByType = new ArrayList<>();
        for (Room room : rooms){
            if (room.getRoomType().equals(roomType) && room.getStatus()) {
                roomsByType.add(room);
            }
        }

        rooms.clear();

        for (Room r : roomsByType){
            if (isRoomAvailable(r.getId(),checkIn,checkOut)){
                rooms.add(r);
            }
        }

        return rooms;
    }

    public void addRoom(Room room) {
        if (!roomRepository.existsByRoomNumber(room.getRoomNumber())){
            room.setStatus(true);
            roomRepository.save(room); //Можем да имаме еднакви стаи в хотела
        }else {
            throw new IllegalArgumentException("Не могат две стаи да са с един и същ номер");
        }
    }


    public void deactivateRoom(int roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Стаята не съществува"));

        if (room.getStatus()) {
            throw new RuntimeException("Стаята е заета и не може да бъде деактивирана");
        }

        room.setStatus(false);
        roomRepository.save(room);
    }

    public void reactivateRoom(int roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Стаята не съществува"));

        room.setStatus(true);
        roomRepository.save(room);
    }

    public void addBenefit(int roomId, String benefit) {
        if (existRoomById(roomId)) {
            Room room = roomRepository.getReferenceById(roomId);

            List<String> benefits = Arrays.stream(room.getBenefits().split(", ")).toList();
            if (!benefits.contains(benefit)) {
                room.setBenefits(room.getBenefits() + ", " + benefit);
                roomRepository.save(room);
            }else{
                throw new IllegalArgumentException("Стаята съдържа това удобство");
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
                String updatedBenefits = String.join(", ", benefits);
                room.setBenefits(updatedBenefits);
                roomRepository.save(room);
            } else {
                throw new IllegalArgumentException("Стаята не съдържа това удобство");
            }
        }
    }

    public void changePrice(int roomId, double price){
        if (existRoomById(roomId)){
            roomRepository.getReferenceById(roomId).setPrice(price);
            roomRepository.save(roomRepository.getReferenceById(roomId));
        }else {throw new IllegalArgumentException("Стаята не съществува");}
    }

    boolean existRoomById(int roomId) {
        if (!roomRepository.existsById(roomId)) {
            throw new IllegalArgumentException("Стаята не съществува");
        }
        return true;
    }

}

