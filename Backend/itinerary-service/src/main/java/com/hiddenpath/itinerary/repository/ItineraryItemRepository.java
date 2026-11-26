package com.hiddenpath.itinerary.repository;

import com.hiddenpath.itinerary.entity.ItineraryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ItineraryItemRepository extends JpaRepository<ItineraryItem, UUID> {
}