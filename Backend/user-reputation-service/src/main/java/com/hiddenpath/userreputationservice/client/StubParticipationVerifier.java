package com.hiddenpath.userreputationservice.client;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class StubParticipationVerifier implements ParticipationVerifier {
    @Override
    public boolean hasParticipated(UUID userId, UUID experienceId) {
        // TODO: replace with a Feign client calling Booking (booking-service)
        return true;
    }
}