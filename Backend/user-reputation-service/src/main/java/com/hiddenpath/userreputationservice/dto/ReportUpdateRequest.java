package com.hiddenpath.userreputationservice.dto;

import com.hiddenpath.userreputationservice.entity.enums.ReportStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReportUpdateRequest(
        @NotNull ReportStatus status,
        @Size(max = 1000) String adminNote) {}
