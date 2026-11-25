package com.hiddenpath.userreputationservice.dto;

import com.hiddenpath.userreputationservice.entity.User;
import com.hiddenpath.userreputationservice.entity.enums.UserStatus;

import java.time.Instant;
import java.util.UUID;

public record UserResponse(
        UUID id, String email, String firstName, String lastName,
        String phone, String country, String avatarUrl, String bio,
        UserStatus status, Instant createdAt) {

    public static UserResponse from(User u) {
        return new UserResponse(u.getId(), u.getEmail(), u.getFirstName(), u.getLastName(),
                u.getPhone(), u.getCountry(), u.getAvatarUrl(), u.getBio(),
                u.getStatus(), u.getCreatedAt());
    }
}
