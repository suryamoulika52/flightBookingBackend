//package com.flightbooking.controller;
//
//import java.util.List;
//
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.flightbooking.entity.Booking;
//import com.flightbooking.service.BookingService;
//import org.springframework.web.bind.annotation.CrossOrigin;
//@CrossOrigin(origins = {
//	    "http://localhost:5173",
//	    "http://localhost:5176",
//	    "http://localhost:5174",
//	    "http://localhost:5175"
//	})
//@RestController
//@RequestMapping("/api/bookings")
//public class BookingController {
//
//    private final BookingService bookingService;
//
//    public BookingController(BookingService bookingService) {
//        this.bookingService = bookingService;
//    }
//
//    // Create Booking
//    @PostMapping
//    public Booking createBooking(
//            @RequestParam Long flightId,
//            @RequestParam Long passengerId,
//            @RequestParam Integer numberOfSeats) {
//
//        return bookingService.createBooking(
//                flightId,
//                passengerId,
//                numberOfSeats
//        );
//    }
//
//    // Get All Bookings
//    @GetMapping
//    public List<Booking> getAllBookings() {
//        return bookingService.getAllBookings();
//    }
//
//    // Get Booking By ID
//    @GetMapping("/{id}")
//    public Booking getBookingById(@PathVariable Long id) {
//        return bookingService.getBookingById(id);
//    }
//
//    // Delete Booking
//    @DeleteMapping("/{id}")
//    public String deleteBooking(@PathVariable Long id) {
//
//        bookingService.deleteBooking(id);
//
//        return "Booking Deleted Successfully";
//    }
//}
package com.flightbooking.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flightbooking.entity.Booking;
import com.flightbooking.service.BookingService;

@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://localhost:5174",
        "http://localhost:5175",
        "http://localhost:5176"
})
@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // Create Booking
    @PostMapping
    public Booking createBooking(
            @RequestParam Long flightId,
            @RequestParam Long passengerId,
            @RequestParam Integer numberOfSeats) {

        return bookingService.createBooking(
                flightId,
                passengerId,
                numberOfSeats
        );
    }

    // Get All Bookings
    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    // Get Booking By ID
    @GetMapping("/{id}")
    public Booking getBookingById(
            @PathVariable Long id) {

        return bookingService.getBookingById(id);
    }

    // Cancel Booking
    @DeleteMapping("/{id}")
    public String deleteBooking(
            @PathVariable Long id) {

        bookingService.deleteBooking(id);

        return "Booking cancelled successfully";
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }
}