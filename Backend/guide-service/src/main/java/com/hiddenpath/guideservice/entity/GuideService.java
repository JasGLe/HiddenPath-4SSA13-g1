
package com.hiddenpath.guideservice.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "guide_services")
public class GuideService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String prestationSpecialite;

    @ManyToOne
    @JoinColumn(name = "guide_id", nullable = false)
    private Guide guide;

    // Empty constructor
    public GuideService() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getPrestationSpecialite() {
        return prestationSpecialite;
    }

    public void setPrestationSpecialite(String prestationSpecialite) {
        this.prestationSpecialite = prestationSpecialite;
    }

    public Guide getGuide() {
        return guide;
    }

    public void setGuide(Guide guide) {
        this.guide = guide;
    }
}
