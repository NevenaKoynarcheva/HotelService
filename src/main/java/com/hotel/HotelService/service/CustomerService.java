package com.hotel.HotelService.service;
import com.hotel.HotelService.model.Customer;
import com.hotel.HotelService.model.Room;
import com.hotel.HotelService.model.RoomType;
import com.hotel.HotelService.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RoomService roomService;
    private Customer getCustomer(String username) {
        return customerRepository.findByUsername(username).orElse(null);
    }

    public void logIn(String username, String password) {
        if (!customerRepository.existsByUsername(username)) {
            throw new RuntimeException("User does not exist. Please register first.");
        } else {
            Customer customer = getCustomer(username);
            if (customer != null) {
                if (!customer.getPassword().equals(password)) {
                    throw new RuntimeException("Invalid password");
                }
            }
            // Proceed with login logic (e.g., setting a session or returning a token)
        }
    }

    public void saveUser(String username, String password, String email) {
        if (customerRepository.existsByUsername(username)) {
            throw new RuntimeException("Вече е регистриран потребител с този username");
        }

        if (customerRepository.existsByEmail(email)) {
            throw new RuntimeException("Вече е регистриран потребител с този email");
        }

        Customer customer = new Customer();
        customer.setUsername(username);
        customer.setPassword(password); // In a real application, you should hash the password
        customer.setEmail(email);
        customerRepository.save(customer);
    }

    public List<Room> findAvailableRoom(RoomType roomType, LocalDate checkIn, LocalDate checkOut){
        return roomService.findAvailableRoomsByType(roomType, checkIn, checkOut);
    }

    public Room checkIn(int roomId, LocalDate checkIn){
        return roomService.checkIn(roomId, checkIn);
    }

    public Room checkOut(int roomId, LocalDate checkOut){
        return roomService.checkOut(roomId, checkOut);
    }



}
