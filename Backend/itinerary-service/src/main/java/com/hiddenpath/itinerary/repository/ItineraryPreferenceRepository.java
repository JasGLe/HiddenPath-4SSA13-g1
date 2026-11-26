package com.hiddenpath.itinerary.repository;

import com.hiddenpath.itinerary.entity.ItineraryPreference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ItineraryPreferenceRepository extends JpaRepository<ItineraryPreference, UUID> {
}