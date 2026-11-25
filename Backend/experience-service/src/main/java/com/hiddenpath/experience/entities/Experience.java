package com.hiddenpath.experience.entities;

import com.hiddenpath.experience.enums.ExperienceStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "experiences")
@Getter
@Setter
@NoArgsConstructor
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID guideId;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(precision = 12, scale = 2)
    private BigDecimal price;

    private int durationMinutes;

    private int maxParticipants;

    private String city;

    @Enumerated(EnumType.STRING)
    private ExperienceStatus status = ExperienceStatus.DRAFT;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "experience", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<ExperienceImage> images = new java.util.ArrayList<>();

    public void addImage(ExperienceImage image) {
        images.add(image);
        image.setExperience(this);
    }

    public void removeImage(ExperienceImage image) {
        images.remove(image);
        image.setExperience(null);
    }
}
