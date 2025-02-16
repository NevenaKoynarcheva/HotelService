package com.hotel.HotelService.model;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;
import java.util.List;
@Entity
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private int roomNumber;
    @NotNull
    @Enumerated(EnumType.STRING)
    private RoomType roomType;
    @NotNull
    private double price;
    @NotNull
    private String benefits;
    // Convert list to string
    //  String benefitsString = String.join(",", benefitsList);

    // Convert string to list
    //List<String> benefitsList = Arrays.asList(benefitsString.split(","));
    private LocalDate checkIn;
    private LocalDate checkOut;
    @NotNull
    private boolean bookingStatus;

    public Room(int id, int roomNumber, RoomType roomType, double price, String benefits, LocalDate checkIn, LocalDate checkOut, boolean bookingStatus) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
        this.benefits = benefits;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.bookingStatus = bookingStatus;
    }

    public Room() {
    }

    ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public boolean getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(boolean bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

}
