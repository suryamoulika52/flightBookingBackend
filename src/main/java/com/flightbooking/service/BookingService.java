package com.flightbooking.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flightbooking.entity.Booking;
import com.flightbooking.entity.Flight;
import com.flightbooking.entity.Passenger;
import com.flightbooking.repository.BookingRepository;
import com.flightbooking.repository.FlightRepository;
import com.flightbooking.repository.PassengerRepository;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final PassengerRepository passengerRepository;

    public BookingService(
            BookingRepository bookingRepository,
            FlightRepository flightRepository,
            PassengerRepository passengerRepository) {

        this.bookingRepository = bookingRepository;
        this.flightRepository = flightRepository;
        this.passengerRepository = passengerRepository;
    }

    // =====================================================
    // CREATE BOOKING
    // =====================================================

    @Transactional
    public Booking createBooking(
            Long flightId,
            Long passengerId,
            Integer numberOfSeats) {

        System.out.println("=================================");
        System.out.println("CREATE BOOKING");
        System.out.println("Flight ID      : " + flightId);
        System.out.println("Passenger ID   : " + passengerId);
        System.out.println("Number of seats: " + numberOfSeats);

        // Validate number of seats
        if (numberOfSeats == null || numberOfSeats <= 0) {
            throw new RuntimeException(
                    "Number of seats must be greater than 0"
            );
        }

        // Find flight
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Flight not found: " + flightId
                        ));

        System.out.println(
                "Flight found: " + flight.getFlightNumber()
        );

        System.out.println(
                "Available seats before booking: "
                        + flight.getAvailableSeats()
        );

        // Check available seats
        if (flight.getAvailableSeats() < numberOfSeats) {
            throw new RuntimeException(
                    "Not enough seats available. Available seats: "
                            + flight.getAvailableSeats()
            );
        }

        // Find passenger
        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Passenger not found: " + passengerId
                        ));

        System.out.println(
                "Passenger found: " + passenger.getName()
        );

        // Reduce available seats
        flight.setAvailableSeats(
                flight.getAvailableSeats() - numberOfSeats
        );

        flightRepository.saveAndFlush(flight);

        System.out.println(
                "Available seats after booking: "
                        + flight.getAvailableSeats()
        );

        // Create booking
        Booking booking = new Booking();

        booking.setBookingDate(LocalDateTime.now());
        booking.setNumberOfSeats(numberOfSeats);
        booking.setStatus("CONFIRMED");
        booking.setFlight(flight);
        booking.setPassenger(passenger);

        System.out.println("Saving booking...");

        // Force INSERT immediately
        Booking savedBooking =
                bookingRepository.saveAndFlush(booking);

        System.out.println(
                "Booking saved successfully. ID: "
                        + savedBooking.getId()
        );

        System.out.println("=================================");

        return savedBooking;
    }

    // =====================================================
    // GET ALL BOOKINGS
    // =====================================================

    public List<Booking> getAllBookings() {

        return bookingRepository.findAll();
    }

    // =====================================================
    // GET BOOKING BY ID
    // =====================================================

    public Booking getBookingById(Long id) {

        return bookingRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Booking not found"
                        ));
    }

    // =====================================================
    // CANCEL BOOKING
    // =====================================================

    @Transactional
    public void deleteBooking(Long id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Booking not found"
                        ));

        // Prevent cancelling twice
        if ("CANCELLED".equalsIgnoreCase(
                booking.getStatus())) {

            throw new RuntimeException(
                    "Booking is already cancelled"
            );
        }

        Flight flight = booking.getFlight();

        if (flight == null) {
            throw new RuntimeException(
                    "Flight associated with booking not found"
            );
        }

        // Restore seats
        flight.setAvailableSeats(
                flight.getAvailableSeats()
                        + booking.getNumberOfSeats()
        );

        flightRepository.saveAndFlush(flight);

        // Change booking status
        booking.setStatus("CANCELLED");

        bookingRepository.saveAndFlush(booking);
    }
}