package com.hiddenpath.userreputationservice.dto;

import com.hiddenpath.userreputationservice.entity.Review;

import java.time.Instant;
import java.util.UUID;

public record ReviewResponse(UUID id, UUID authorId, String authorName, UUID experienceId,
                             UUID guideId, int rating, String comment, Instant createdAt) {

    public static ReviewResponse from(Review r) {
        String last = r.getAuthor().getLastName();
        String name = r.getAuthor().getFirstName()
                + (last == null || last.isBlank() ? "" : " " + last.charAt(0) + ".");
        return new ReviewResponse(r.getId(), r.getAuthor().getId(), name, r.getExperienceId(),
                r.getGuideId(), r.getRating(), r.getComment(), r.getCreatedAt());
    }
}