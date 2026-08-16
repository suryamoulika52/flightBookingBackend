package com.flightbooking.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import com.flightbooking.entity.Flight;
import com.flightbooking.service.FlightService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;
@CrossOrigin(origins = {
	    "http://localhost:5173",
	    "http://localhost:5176",
	    "http://localhost:5174",
	    "http://localhost:5175"
	})
@RestController
@RequestMapping("/api/flights")

public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping
    public Flight addFlight(@Valid @RequestBody Flight flight) {

       

        return flightService.addFlight(flight);
    }
   

    @GetMapping
    public List<Flight> getAllFlights() {
        return flightService.getAllFlights();
    }
    @GetMapping("/{id}")
    public Flight getFlight(@PathVariable Long id) {
        return flightService.getFlightById(id);
    }
    @GetMapping("/search")
    public List<Flight> searchFlights(
            @RequestParam String source,
            @RequestParam String destination) {

        return flightService.searchFlights(source, destination);
    }

    @PutMapping("/{id}")
    public Flight updateFlight(@PathVariable Long id,@Valid
                               @RequestBody Flight flight) {
        return flightService.updateFlight(id, flight);
    }

    @DeleteMapping("/{id}")
    public String deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
        return "Flight Deleted Successfully";
    }
}