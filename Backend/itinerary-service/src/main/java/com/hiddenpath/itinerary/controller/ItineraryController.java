package com.hiddenpath.itinerary.controller;

import com.hiddenpath.itinerary.dto.GenerateItineraryRequest;
import com.hiddenpath.itinerary.dto.ItineraryResponse;
import com.hiddenpath.itinerary.service.ItineraryService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/itineraries")
public class ItineraryController {

    private final ItineraryService itineraryService;

    public ItineraryController(ItineraryService itineraryService) {
        this.itineraryService = itineraryService;
    }

    // Generate an itinerary
    @PostMapping("/generate")
    public ResponseEntity<ItineraryResponse> generateItinerary(
            @RequestBody GenerateItineraryRequest request) {

        ItineraryResponse response =
                itineraryService.generateItinerary(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Get an itinerary by ID
    @GetMapping("/{id}")
    public ResponseEntity<ItineraryResponse> getItineraryById(
            @PathVariable UUID id) {

        return ResponseEntity.ok(
                itineraryService.getItineraryById(id)
        );
    }

    // Update an itinerary
    @PutMapping("/{id}")
    public ResponseEntity<ItineraryResponse> updateItinerary(
            @PathVariable UUID id,
            @RequestBody GenerateItineraryRequest request) {

        return ResponseEntity.ok(
                itineraryService.updateItinerary(id, request)
        );
    }

    // Delete an itinerary
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItinerary(
            @PathVariable UUID id) {

        itineraryService.deleteItinerary(id);

        return ResponseEntity.noContent().build();
    }
}