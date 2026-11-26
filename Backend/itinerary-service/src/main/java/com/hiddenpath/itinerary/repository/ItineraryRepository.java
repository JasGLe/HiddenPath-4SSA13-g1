package com.hiddenpath.itinerary.repository;

import com.hiddenpath.itinerary.entity.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ItineraryRepository extends JpaRepository<Itinerary, UUID> {
}