package com.flightbooking.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import com.flightbooking.entity.Passenger;
import com.flightbooking.service.PassengerService;
import org.springframework.web.bind.annotation.CrossOrigin;
@CrossOrigin(origins = {
	    "http://localhost:5173",
	    "http://localhost:5176",
	    "http://localhost:5174",
	    "http://localhost:5175"
	})
@RestController
@RequestMapping("/api/passengers")
public class PassengerController {

    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    // Add Passenger
    @PostMapping
    public Passenger addPassenger(@Valid @RequestBody Passenger passenger) {
        return passengerService.addPassenger(passenger);
    }

    // Get All Passengers
    @GetMapping
    public List<Passenger> getAllPassengers() {
        return passengerService.getAllPassengers();
    }

    // Get Passenger By Id
    @GetMapping("/{id}")
    public Passenger getPassengerById(@PathVariable Long id) {
        return passengerService.getPassengerById(id);
    }

    // Update Passenger
    @PutMapping("/{id}")
    public Passenger updatePassenger(
            @PathVariable Long id,
            @Valid @RequestBody Passenger passenger) {

        return passengerService.updatePassenger(id, passenger);
    }

    // Delete Passenger
    @DeleteMapping("/{id}")
    public String deletePassenger(@PathVariable Long id) {
        passengerService.deletePassenger(id);
        return "Passenger Deleted Successfully";
    }
}