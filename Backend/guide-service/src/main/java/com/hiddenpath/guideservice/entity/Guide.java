
package com.hiddenpath.guideservice.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "guides")
public class Guide {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long userId;

    private String profil;

    // A guide can speak multiple languages
    @ManyToMany
    @JoinTable(
            name = "guide_languages",
            joinColumns = @JoinColumn(name = "guide_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id")
    )
    private List<Language> languages = new ArrayList<>();

    // A guide can offer multiple services
    @OneToMany(
            mappedBy = "guide",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<GuideService> services = new ArrayList<>();

    // One availability calendar per guide
    @OneToOne(
            mappedBy = "guide",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private AvailabilityCalendar availabilityCalendar;

    // A guide can have multiple unavailability periods
    @OneToMany(
            mappedBy = "guide",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<UnavailabilityPeriod> unavailabilityPeriods =
            new ArrayList<>();

    public Guide() {
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getProfil() {
        return profil;
    }

    public void setProfil(String profil) {
        this.profil = profil;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

    public List<GuideService> getServices() {
        return services;
    }

    public void setServices(List<GuideService> services) {
        this.services = services;
    }

    public AvailabilityCalendar getAvailabilityCalendar() {
        return availabilityCalendar;
    }

    public void setAvailabilityCalendar(
            AvailabilityCalendar availabilityCalendar) {
        this.availabilityCalendar = availabilityCalendar;
    }

    public List<UnavailabilityPeriod> getUnavailabilityPeriods() {
        return unavailabilityPeriods;
    }

    public void setUnavailabilityPeriods(
            List<UnavailabilityPeriod> unavailabilityPeriods) {
        this.unavailabilityPeriods = unavailabilityPeriods;
    }
}
