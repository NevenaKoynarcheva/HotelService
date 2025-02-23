package com.hotel.HotelService.repository;

import com.hotel.HotelService.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    boolean existsByUsername(String username);
    Optional<Customer> findByUsername(String username);
    boolean existsByEmail(String email);

}
