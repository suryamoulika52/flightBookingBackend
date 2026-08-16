package com.flightbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.flightbooking.entity.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {

}