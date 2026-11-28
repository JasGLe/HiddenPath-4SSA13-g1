package com.hiddenpath.userreputationservice.dto;

import com.hiddenpath.userreputationservice.entity.Report;
import com.hiddenpath.userreputationservice.entity.enums.ReportStatus;
import com.hiddenpath.userreputationservice.entity.enums.ReportTargetType;

import java.time.Instant;
import java.util.UUID;

public record ReportResponse(UUID id, UUID reporterId, ReportTargetType targetType, UUID targetId,
                             String reason, String description, ReportStatus status,
                             String adminNote, Instant createdAt, Instant resolvedAt) {

    public static ReportResponse from(Report r) {
        return new ReportResponse(r.getId(), r.getReporter().getId(), r.getTargetType(), r.getTargetId(),
                r.getReason(), r.getDescription(), r.getStatus(), r.getAdminNote(),
                r.getCreatedAt(), r.getResolvedAt());
    }
}