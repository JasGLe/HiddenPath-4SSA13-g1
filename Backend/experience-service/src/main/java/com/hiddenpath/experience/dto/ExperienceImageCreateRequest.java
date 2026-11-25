package com.hiddenpath.experience.dto;

import jakarta.validation.constraints.NotBlank;

public record ExperienceImageCreateRequest(
        @NotBlank(message = "L'URL de l'image est obligatoire.")
        String url,

        boolean cover
) {
}