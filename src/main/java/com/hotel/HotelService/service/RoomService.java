package com.hotel.HotelService.service;
import com.hotel.HotelService.model.Room;
import com.hotel.HotelService.model.RoomType;
import com.hotel.HotelService.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;
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


//    public void deleteRoom(int id){
//        if (findRoomById(id)!=null){
//            roomRepository.deleteById(id);
//        }else{
//            throw new IllegalArgumentException("Липсва такава стая");
//        }
//
//    }

    public boolean isRoomAvailable(int roomId, LocalDate checkIn, LocalDate checkOut) {
        if (checkIn.isAfter(checkOut)) {
            throw new IllegalArgumentException("Невалидни дати: checkIn трябва да е преди checkOut");
        }

        Room room = findRoomById(roomId);
        if (!room.getBookingStatus()) {
            return true;
        }

        LocalDate existingCheckIn = room.getCheckIn();
        LocalDate existingCheckOut = room.getCheckOut();
        return checkIn.isAfter(existingCheckOut) || checkOut.isBefore(existingCheckIn);
    }

     public Room checkIn(int roomId, LocalDate checkInDate) {
        Room room = findRoomById(roomId);
        if (room.getBookingStatus()) {
            throw new RuntimeException("Стаята е заета");
        }

        room.setCheckIn(checkInDate);
        room.setBookingStatus(true);
        return roomRepository.save(room);
    }

    public Room checkOut(int roomId, LocalDate checkOutDate) {
        Room room = findRoomById(roomId);
        if (!room.getBookingStatus()) {
            throw new RuntimeException("Стаята е свободна");
        }
        room.setCheckOut(checkOutDate);
        room.setBookingStatus(false);
        return roomRepository.save(room);
    }


    public List<Room> findAvailableRoomsByType(RoomType roomType, LocalDate checkIn, LocalDate checkOut) {
         List<Room> rooms = findAll();
         List<Room> roomsByType = new ArrayList<>();
         for (Room room : rooms){
             if (room.getRoomType().equals(roomType) && room.isActives()) {
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
            room.setActives(true);
            roomRepository.save(room); //Можем да имаме еднакви стаи в хотела
        }else {
            throw new IllegalArgumentException("Не могат две стаи да са с един и същ номер");
        }
    }


    public void deactivateRoom(int roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Стаята не съществува"));

        if (room.getBookingStatus()) {
            throw new RuntimeException("Стаята е заета и не може да бъде деактивирана");
        }

        room.setActives(false);
        roomRepository.save(room);
    }

    public void reactivateRoom(int roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Стаята не съществува"));

        room.setActives(true);
        roomRepository.save(room);
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

     boolean existRoomById(int roomId) {
        if (!roomRepository.existsById(roomId)) {
            throw new IllegalArgumentException("Стаята не съществува");
        }
        return true;
    }

}

//can be used : public List<Room> findAvailableRoomsByType(RoomType roomType, LocalDate checkIn, LocalDate checkOut) {
//    // Fetch rooms of the specified type directly from the repository
//    List<Room> roomsByType = roomRepository.findByRoomType(roomType);
//
//    // Filter rooms by availability
//    List<Room> availableRooms = new ArrayList<>();
//    for (Room room : roomsByType) {
//        if (isRoomAvailable(room.getId(), checkIn, checkOut)) {
//            availableRooms.add(room);
//        }
//    }
//
//    return availableRooms;
//}public interface RoomRepository extends JpaRepository<Room, Integer> {
//    List<Room> findByRoomType(RoomType roomType); // Add this method
//}