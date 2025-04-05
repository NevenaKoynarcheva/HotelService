package com.hotel.HotelService.repository;

import com.hotel.HotelService.model.Reservation;
import com.hotel.HotelService.model.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findByRoomIdAndStatusNot(int roomId, ReservationStatus status);

    List<Reservation> findByCustomerId(int customerId);


}