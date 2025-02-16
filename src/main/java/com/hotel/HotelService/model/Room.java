package com.hotel.HotelService.model;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

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
    private String roomType;
    @NotNull
    private double price;
    @NotNull
    private List<String> benefits;
    @NotNull
    private boolean bookingStatus;

    public Room(int id, int roomNumber, String roomType, double price, List<String> benefits, boolean bookingStatus) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
        this.benefits = benefits;
        this.bookingStatus = bookingStatus;
    }

    public Room(){};

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

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<String> getBenefits() {
        return benefits;
    }

    public void setBenefits(List<String> benefits) {
        this.benefits = benefits;
    }

    public boolean getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(boolean bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
}
