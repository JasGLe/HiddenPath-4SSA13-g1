
package com.hiddenpath.booking.service;

import com.hiddenpath.booking.dto.BookingResponse;
import com.hiddenpath.booking.entity.Booking;
import com.hiddenpath.booking.entity.BookingStatus;
import com.hiddenpath.booking.exception.BookingNotFoundException;
import com.hiddenpath.booking.mapper.BookingMapper;
import com.hiddenpath.booking.repository.BookingRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    // Create a booking (temporary implementation)
    public BookingResponse createBooking(Booking booking) {

        if (booking == null) {
            throw new IllegalArgumentException("Booking cannot be null");
        }

        if (booking.getUserId() == null ||
                booking.getExperienceId() == null ||
                booking.getBookingDate() == null ||
                booking.getTotalPrice() == null) {

            throw new IllegalArgumentException(
                    "Required booking information is missing"
            );
        }

        if (booking.getNumberOfParticipants() <= 0) {
            throw new IllegalArgumentException(
                    "Number of participants must be greater than zero"
            );
        }

        if (booking.getTotalPrice().signum() < 0) {
            throw new IllegalArgumentException(
                    "Total price cannot be negative"
            );
        }

        if (booking.getParticipants() == null ||
                booking.getParticipants().isEmpty()) {
            throw new IllegalArgumentException(
                    "At least one participant is required"
            );
        }

        if (booking.getNumberOfParticipants()
                != booking.getParticipants().size()) {
            throw new IllegalArgumentException(
                    "Participant count does not match the participant list"
            );
        }

        booking.setStatus(BookingStatus.PENDING);

        Booking savedBooking = bookingRepository.save(booking);

        return BookingMapper.toResponse(savedBooking);
    }

    // Get booking by ID
    @Transactional(readOnly = true)
    public BookingResponse getBookingById(UUID id) {

        return BookingMapper.toResponse(findBooking(id));
    }

    // Get all bookings belonging to a user
    @Transactional(readOnly = true)
    public List<BookingResponse> getBookingsByUserId(UUID userId) {

        if (userId == null) {
            throw new IllegalArgumentException("User ID is required");
        }

        return bookingRepository
                .findByUserIdOrderByBookingDateDesc(userId)
                .stream()
                .map(BookingMapper::toResponse)
                .toList();
    }

    // Confirm booking
    public BookingResponse confirmBooking(UUID id) {

        Booking booking = findBooking(id);

        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new IllegalStateException(
                    "Only pending bookings can be confirmed"
            );
        }

        booking.setStatus(BookingStatus.CONFIRMED);

        return BookingMapper.toResponse(booking);
    }

    // Cancel booking
    public BookingResponse cancelBooking(UUID id) {

        Booking booking = findBooking(id);

        if (booking.getStatus() == BookingStatus.CANCELLED ||
                booking.getStatus() == BookingStatus.COMPLETED) {

            throw new IllegalStateException(
                    "This booking cannot be cancelled"
            );
        }

        booking.setStatus(BookingStatus.CANCELLED);

        return BookingMapper.toResponse(booking);
    }

    // Validate attendance
    public BookingResponse validateAttendance(UUID id) {

        Booking booking = findBooking(id);

        if (booking.getStatus() != BookingStatus.CONFIRMED) {
            throw new IllegalStateException(
                    "Only confirmed bookings can have attendance validated"
            );
        }

        booking.setStatus(BookingStatus.COMPLETED);

        return BookingMapper.toResponse(booking);
    }

    // Internal helper method
    private Booking findBooking(UUID id) {

        if (id == null) {
            throw new IllegalArgumentException("Booking ID is required");
        }

        return bookingRepository.findById(id)
                .orElseThrow(() ->
                        new BookingNotFoundException(
                                "Booking not found: " + id
                        )
                );
    }
}
