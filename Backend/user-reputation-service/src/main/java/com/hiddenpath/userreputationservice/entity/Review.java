package com.hiddenpath.userreputationservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "reviews",
        uniqueConstraints = @UniqueConstraint(columnNames = {"author_id", "experienceId"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id")
    private User author;

    @Column(nullable = false)
    private UUID experienceId;          // external reference (Experience service)

    @Column(nullable = false)
    private UUID guideId;               // external reference (Guide service)

    @Column(nullable = false)
    private int rating;                 // 1..5, validated in the DTO later

    @Column(length = 2000)
    private String comment;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant createdAt;
}
