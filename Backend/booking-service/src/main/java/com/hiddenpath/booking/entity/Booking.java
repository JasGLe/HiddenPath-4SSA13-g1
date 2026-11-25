
package com.hiddenpath.booking.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // External reference to User & Reputation service
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    // External reference to Experience service
    @Column(name = "experience_id", nullable = false)
    private UUID experienceId;

    @Column(name = "booking_date", nullable = false)
    private LocalDate bookingDate;

    @Column(name = "number_of_participants", nullable = false)
    private int numberOfParticipants;

    @Column(
            name = "total_price",
            nullable = false,
            precision = 10,
            scale = 2
    )
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;

    // One booking has one or more participants
    @OneToMany(
            mappedBy = "booking",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<BookingParticipant> participants = new ArrayList<>();

    public Booking() {
    }

    // Getters and Setters

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

    public List<BookingParticipant> getParticipants() {
        return Collections.unmodifiableList(participants);
    }

    public void setParticipants(List<BookingParticipant> participants) {

        // Disconnect previous participants
        for (BookingParticipant participant :
                new ArrayList<>(this.participants)) {
            removeParticipant(participant);
        }

        // Add new participants
        if (participants != null) {
            participants.forEach(this::addParticipant);
        }
    }

    // Relationship helper methods

    public void addParticipant(BookingParticipant participant) {

        if (participant != null && !participants.contains(participant)) {
            participants.add(participant);
            participant.setBooking(this);
        }
    }

    public void removeParticipant(BookingParticipant participant) {

        if (participant != null && participants.remove(participant)) {
            participant.setBooking(null);
        }
    }
}
