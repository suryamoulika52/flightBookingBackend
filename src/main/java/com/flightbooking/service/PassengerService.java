package com.flightbooking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.flightbooking.entity.Passenger;
import com.flightbooking.repository.PassengerRepository;

@Service
public class PassengerService {

    private final PassengerRepository passengerRepository;

    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    // Add Passenger
    public Passenger addPassenger(Passenger passenger) {
        return passengerRepository.save(passenger);
    }

    // Get All Passengers
    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }

    // Get Passenger By Id
   public Passenger getPassengerById(Long id) {

    return passengerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Passenger not found"));
}

    // Update Passenger
    public Passenger updatePassenger(Long id, Passenger passenger) {

        Passenger existingPassenger = passengerRepository.findById(id).orElse(null);

        if (existingPassenger != null) {

            existingPassenger.setName(passenger.getName());
            existingPassenger.setEmail(passenger.getEmail());
            existingPassenger.setPhone(passenger.getPhone());
            existingPassenger.setAge(passenger.getAge());
            existingPassenger.setGender(passenger.getGender());

            return passengerRepository.save(existingPassenger);
        }

        return null;
    }

    // Delete Passenger
    public void deletePassenger(Long id) {
        passengerRepository.deleteById(id);
    }
}