package com.hiddenpath.booking.dto;

import com.hiddenpath.booking.entity.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookingResponse {

    private UUID id;
    private UUID userId;
    private UUID experienceId;
    private LocalDate bookingDate;
    private int numberOfParticipants;
    private BigDecimal totalPrice;
    private BookingStatus status;
    private List<BookingParticipantResponse> participants = new ArrayList<>();

    public BookingResponse() {
    }

    public BookingResponse(
            UUID id,
            UUID userId,
            UUID experienceId,
            LocalDate bookingDate,
            int numberOfParticipants,
            BigDecimal totalPrice,
            BookingStatus status,
            List<BookingParticipantResponse> participants
    ) {
        this.id = id;
        this.userId = userId;
        this.experienceId = experienceId;
        this.bookingDate = bookingDate;
        this.numberOfParticipants = numberOfParticipants;
        this.totalPrice = totalPrice;
        this.status = status;
        setParticipants(participants);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
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

    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public void setNumberOfParticipants(int numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public List<BookingParticipantResponse> getParticipants() {
        return participants;
    }

    public void setParticipants(List<BookingParticipantResponse> participants) {
        this.participants = participants == null
                ? new ArrayList<>()
                : new ArrayList<>(participants);
    }
}