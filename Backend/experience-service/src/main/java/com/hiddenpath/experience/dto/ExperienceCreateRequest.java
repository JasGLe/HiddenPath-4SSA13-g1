package com.hiddenpath.experience.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

public record ExperienceCreateRequest(
        @NotNull(message = "Le guide est obligatoire.")
        UUID guideId,

        @NotBlank(message = "Le titre est obligatoire.")
        @Size(max = 255, message = "Le titre ne doit pas depasser 255 caracteres.")
        String title,

        @NotBlank(message = "La description est obligatoire.")
        String description,

        @NotNull(message = "Le prix est obligatoire.")
        @PositiveOrZero(message = "Le prix doit etre positif ou nul.")
        @Digits(integer = 10, fraction = 2,
                message = "Le prix doit contenir au maximum 10 chiffres entiers et 2 decimales.")
        BigDecimal price,

        @NotNull(message = "La duree est obligatoire.")
        @Positive(message = "La duree doit etre strictement positive.")
        Integer durationMinutes,

        @NotNull(message = "Le nombre de participants est obligatoire.")
        @Positive(message = "Le nombre de participants doit etre strictement positif.")
        Integer maxParticipants,

        @NotBlank(message = "La ville est obligatoire.")
        @Size(max = 255, message = "La ville ne doit pas depasser 255 caracteres.")
        String city,

        @NotNull(message = "La categorie est obligatoire.")
        UUID categoryId
) {
}
