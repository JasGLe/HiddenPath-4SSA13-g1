package com.hiddenpath.experience.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequest(
        @NotBlank(message = "Le nom de la categorie est obligatoire.")
        @Size(max = 255, message = "Le nom ne doit pas depasser 255 caracteres.")
        String name
) {
}
