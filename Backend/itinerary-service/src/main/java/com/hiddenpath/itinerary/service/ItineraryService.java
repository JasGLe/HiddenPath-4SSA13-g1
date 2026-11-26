package com.hiddenpath.itinerary.service;

import com.hiddenpath.itinerary.dto.GenerateItineraryRequest;
import com.hiddenpath.itinerary.dto.ItineraryResponse;

import java.util.UUID;

public interface ItineraryService {

    ItineraryResponse generateItinerary(GenerateItineraryRequest request);

    ItineraryResponse getItineraryById(UUID id);

    ItineraryResponse updateItinerary(
            UUID id,
            GenerateItineraryRequest request
    );

    void deleteItinerary(UUID id);
}