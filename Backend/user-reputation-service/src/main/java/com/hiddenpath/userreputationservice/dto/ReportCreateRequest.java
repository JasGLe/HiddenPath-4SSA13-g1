package com.hiddenpath.userreputationservice.dto;

import com.hiddenpath.userreputationservice.entity.enums.ReportTargetType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record ReportCreateRequest(
        @NotNull ReportTargetType targetType,
        @NotNull UUID targetId,
        @NotBlank String reason,
        @Size(max = 2000) String description) {}
