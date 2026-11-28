package com.hiddenpath.userreputationservice.client;

import java.util.UUID;

public interface ParticipationVerifier {
    boolean hasParticipated(UUID userId, UUID experienceId);
}