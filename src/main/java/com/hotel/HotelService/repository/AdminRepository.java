package com.hotel.HotelService.repository;
import com.hotel.HotelService.model.Admin;

import com.hotel.HotelService.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    boolean existsByUsername(String username);
    Optional<Admin> findByUsername(String username);
}
