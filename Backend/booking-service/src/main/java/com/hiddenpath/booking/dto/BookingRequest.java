
package com.hiddenpath.booking.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class BookingRequest {

    @NotNull(message = "Experience ID is required")
    private UUID experienceId;

    @NotNull(message = "Booking date is required")
    @FutureOrPresent(message = "Booking date cannot be in the past")
    private LocalDate bookingDate;

    @NotEmpty(message = "At least one participant is required")
    @Size(max = 100, message = "Too many participants")
    @Valid
    private List<BookingParticipantRequest> participants;

    public BookingRequest() {
    }

    public BookingRequest(
            UUID experienceId,
            LocalDate bookingDate,
            List<BookingParticipantRequest> participants) {
        this.experienceId = experienceId;
        this.bookingDate = bookingDate;
        this.participants = participants;
    }

    public UUID getExperienceId() {
        return experienceId;
    }

    public void setExperienceId(UUID experienceId) {
        this.experienceId = experienceId;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public List<BookingParticipantRequest> getParticipants() {
        return participants;
    }

    public void setParticipants(
            List<BookingParticipantRequest> participants) {
        this.participants = participants;
    }
}
