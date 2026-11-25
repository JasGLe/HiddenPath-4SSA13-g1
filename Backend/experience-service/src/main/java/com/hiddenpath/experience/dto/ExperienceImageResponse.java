package com.hiddenpath.experience.dto;

import java.util.UUID;

public record ExperienceImageResponse(
        UUID id,
        String url,
        boolean cover,
        UUID experienceId
) {
}