package com.hiddenpath.userreputationservice.dto;

import jakarta.validation.constraints.Size;

public record UserUpdateRequest(
        String firstName,
        String lastName,
        String phone,
        String country,
        String avatarUrl,
        @Size(max = 1000) String bio) {}
