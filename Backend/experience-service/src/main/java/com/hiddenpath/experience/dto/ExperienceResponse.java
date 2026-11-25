package com.hiddenpath.experience.dto;

import com.hiddenpath.experience.enums.ExperienceStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record ExperienceResponse(
        UUID id,
        UUID guideId,
        String title,
        String description,
        BigDecimal price,
        int durationMinutes,
        int maxParticipants,
        String city,
        ExperienceStatus status,
        UUID categoryId
) {
}
