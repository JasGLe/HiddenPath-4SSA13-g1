package com.hiddenpath.userreputationservice.dto;

import java.util.Map;
import java.util.UUID;

public record ReputationResponse(UUID guideId, double averageRating, long totalReviews,
                                 Map<Integer, Long> distribution) {}