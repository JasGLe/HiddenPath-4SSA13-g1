
package com.hiddenpath.itinerary.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "itineraries")
public class Itinerary {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private String destination;

    private LocalDate startDate;
    private LocalDate endDate;

    @Column(precision = 10, scale = 2)
    private BigDecimal budget;

    @Enumerated(EnumType.STRING)
    private ItineraryStatus status;

    @OneToMany(mappedBy = "itinerary",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<ItineraryItem> items = new ArrayList<>();

    @OneToMany(mappedBy = "itinerary",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<ItineraryPreference> preferences = new ArrayList<>();

    public Itinerary() {
        this.status = ItineraryStatus.DRAFT;
    }

    // Getters and Setters

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public ItineraryStatus getStatus() {
        return status;
    }

    public void setStatus(ItineraryStatus status) {
        this.status = status;
    }

    public List<ItineraryItem> getItems() {
        return items;
    }

    public void setItems(List<ItineraryItem> items) {
        this.items = items;
    }

    public List<ItineraryPreference> getPreferences() {
        return preferences;
    }

    public void setPreferences(List<ItineraryPreference> preferences) {
        this.preferences = preferences;
    }
}
