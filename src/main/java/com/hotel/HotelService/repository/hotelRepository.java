package com.hotel.HotelService.repository;

import com.hotel.HotelService.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface hotelRepository extends JpaRepository<Hotel, Integer> {
}
