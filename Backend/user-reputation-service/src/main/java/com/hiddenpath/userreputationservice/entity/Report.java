package com.hiddenpath.userreputationservice.entity;

import com.hiddenpath.userreputationservice.entity.enums.ReportStatus;
import com.hiddenpath.userreputationservice.entity.enums.ReportTargetType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "reports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reporter_id")
    private User reporter;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportTargetType targetType;

    @Column(nullable = false)
    private UUID targetId;              // id of the reported User or Review

    @Column(nullable = false)
    private String reason;

    @Column(length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private ReportStatus status = ReportStatus.PENDING;

    @Column(length = 1000)
    private String adminNote;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant createdAt;

    private Instant resolvedAt;
}
