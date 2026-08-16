package com.flightbooking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.flightbooking.entity.Flight;
import com.flightbooking.repository.FlightRepository;

@Service
public class FlightService {

    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    // Add Flight
    public Flight addFlight(Flight flight) {
    	if (flight.getAvailableSeats() > flight.getTotalSeats()) {
            throw new RuntimeException(
                "Available seats cannot be greater than total seats"
            );
        }
        return flightRepository.save(flight);
    }

    // Search flights by source and destination
    public List<Flight> searchFlights(String source, String destination) {
        return flightRepository.findBySourceAndDestination(source, destination);
    }

    // Get all flights
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    // Get flight by ID
   
    public Flight getFlightById(Long id) {

        return flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found"));
    }

    // Update Flight
    public Flight updateFlight(Long id, Flight flight) {

        Flight existing = flightRepository.findById(id).orElse(null);

        if (existing != null) {

            existing.setFlightNumber(flight.getFlightNumber());
            existing.setAirline(flight.getAirline());
            existing.setSource(flight.getSource());
            existing.setDestination(flight.getDestination());
            existing.setDepartureTime(flight.getDepartureTime());
            existing.setArrivalTime(flight.getArrivalTime());
            existing.setPrice(flight.getPrice());
            existing.setTotalSeats(flight.getTotalSeats());
            existing.setAvailableSeats(flight.getAvailableSeats());

            return flightRepository.save(existing);
        }

        return null;
    }

    // Delete Flight
    public void deleteFlight(Long id) {
        flightRepository.deleteById(id);
    }
}