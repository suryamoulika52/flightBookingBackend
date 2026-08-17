

package com.flightbooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flightbooking.entity.Booking;
import com.flightbooking.entity.Passenger;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByPassenger(Passenger passenger);

    List<Booking> findByPassengerAndStatus(
            Passenger passenger,
            String status
    );
}
