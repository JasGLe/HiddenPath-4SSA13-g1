package com.hiddenpath.userreputationservice.dto;

import jakarta.validation.constraints.Size;

public record UserCreateRequest(
        String phone,
        String country,
        String avatarUrl,
        @Size(max = 1000) String bio) {}
