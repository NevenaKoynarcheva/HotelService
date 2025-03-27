package com.hotel.HotelService.service;

import com.hotel.HotelService.model.*;
import com.hotel.HotelService.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ReservationService {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private ReservationRepository reservationRepository;
    @Transactional
    public Reservation createReservation(int customerId, int roomId,
                                         LocalDate checkInDate, LocalDate checkOutDate) {
        validateReservationDates(checkInDate, checkOutDate);

        Customer customer = customerService.getCustomerById(customerId);
        Room room = validateRoomAvailability(roomId, checkInDate, checkOutDate);

        Reservation reservation = buildReservation(customer, room, checkInDate, checkOutDate);
        return reservationRepository.save(reservation);
    }

    public boolean isRoomAvailable(int roomId, LocalDate checkIn, LocalDate checkOut) {
        return reservationRepository
                .findConflictingReservations(roomId, checkIn, checkOut, ReservationStatus.CANCELLED)
                .isEmpty();
    }

    @Transactional
    public void cancelReservation(int reservationId) {
        Reservation reservation = getValidReservationForCancellation(reservationId);
        reservation.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);
    }

    public List<Reservation> getCustomerReservations(int customerId) {
        return reservationRepository.findByCustomerId(customerId);
    }

    public List<Room> findAvailableRooms(RoomType roomType, LocalDate checkIn, LocalDate checkOut) {
        List<Room> rooms = roomService.findAll().stream()
                .filter(room -> room.getRoomType() == roomType && room.getStatus())
                .toList();

        return rooms.stream()
                .filter(room -> isRoomAvailable(room.getId(), checkIn, checkOut))
                .toList();
    }

    private void validateReservationDates(LocalDate checkIn, LocalDate checkOut) {
        if (checkIn.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Check-in не може да е в миналото");
        }
        if (checkIn.isAfter(checkOut)) {
            throw new IllegalArgumentException("Check-in трябва да е преди check-out");
        }
    }

    private Room validateRoomAvailability(int roomId, LocalDate checkIn, LocalDate checkOut) {
        Room room = roomService.findRoomById(roomId);
        if (room == null || !room.getStatus()) {
            throw new RuntimeException("Стаята не е свободна");
        }
        if (!isRoomAvailable(roomId, checkIn, checkOut)) {
            throw new RuntimeException("Стаята е вече резервирана за тези дни");
        }
        return room;
    }

    private Reservation buildReservation(Customer customer, Room room,
                                         LocalDate checkIn, LocalDate checkOut) {
        long days = ChronoUnit.DAYS.between(checkIn, checkOut);
        double totalPrice = days * room.getPrice();

        Reservation reservation = new Reservation();
        reservation.setCustomer(customer);
        reservation.setRoom(room);
        reservation.setCheckInDate(checkIn);
        reservation.setCheckOutDate(checkOut);
        reservation.setTotalPrice(totalPrice);
        reservation.setStatus(ReservationStatus.CONFIRMED);
        return reservation;
    }

    private Reservation getValidReservationForCancellation(int reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Не е намерена резервация"));

        if (reservation.getStatus() == ReservationStatus.CANCELLED) {
            throw new RuntimeException("Резервацията вече е отменена");
        }

        if (LocalDate.now().isAfter(reservation.getCheckInDate())) {
            throw new RuntimeException("Не може да бъде отменена след check-in датата");
        }

        return reservation;
    }

}
