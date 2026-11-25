
package com.hiddenpath.booking.controller;

import com.hiddenpath.booking.dto.BookingResponse;
import com.hiddenpath.booking.service.BookingService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // GET: Retrieve a booking by ID
    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBookingById(
            @PathVariable UUID id) {

        return ResponseEntity.ok(
                bookingService.getBookingById(id)
        );
    }

    // GET: Retrieve all bookings belonging to a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingResponse>> getBookingsByUserId(
            @PathVariable UUID userId) {

        return ResponseEntity.ok(
                bookingService.getBookingsByUserId(userId)
        );
    }

    // PUT: Confirm a booking
    @PutMapping("/{id}/confirm")
    public ResponseEntity<BookingResponse> confirmBooking(
            @PathVariable UUID id) {

        return ResponseEntity.ok(
                bookingService.confirmBooking(id)
        );
    }

    // PUT: Cancel a booking
    @PutMapping("/{id}/cancel")
    public ResponseEntity<BookingResponse> cancelBooking(
            @PathVariable UUID id) {

        return ResponseEntity.ok(
                bookingService.cancelBooking(id)
        );
    }

    // PUT: Validate booking attendance
    @PutMapping("/{id}/attendance")
    public ResponseEntity<BookingResponse> validateAttendance(
            @PathVariable UUID id) {

        return ResponseEntity.ok(
                bookingService.validateAttendance(id)
        );
    }
}
