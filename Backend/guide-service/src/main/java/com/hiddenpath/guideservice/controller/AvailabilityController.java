package com.hiddenpath.guideservice.controller;

import com.hiddenpath.guideservice.dto.AvailabilityCheckResponse;
import com.hiddenpath.guideservice.dto.AvailabilitySlotRequest;
import com.hiddenpath.guideservice.dto.AvailabilitySlotResponse;
import com.hiddenpath.guideservice.entity.AvailabilitySlot;
import com.hiddenpath.guideservice.service.AvailabilityManagementService;

import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/guides/{guideId}/availability")
public class AvailabilityController {

    private final AvailabilityManagementService availabilityManagementService;

    public AvailabilityController(
            AvailabilityManagementService availabilityManagementService) {
        this.availabilityManagementService = availabilityManagementService;
    }

    // Add an availability slot
    @PostMapping("/slots")
    public ResponseEntity<AvailabilitySlotResponse> addAvailabilitySlot(
            @PathVariable Long guideId,
            @Valid @RequestBody AvailabilitySlotRequest request) {

        AvailabilitySlot slot = new AvailabilitySlot();
        slot.setStartDateTime(request.getStartDateTime());
        slot.setEndDateTime(request.getEndDateTime());
        slot.setStatus(request.getStatus());

        AvailabilitySlot savedSlot =
                availabilityManagementService.addAvailabilitySlot(guideId, slot);

        AvailabilitySlotResponse response = new AvailabilitySlotResponse(
                savedSlot.getId(),
                savedSlot.getStartDateTime(),
                savedSlot.getEndDateTime(),
                savedSlot.getStatus()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Check guide availability for a requested period
    @GetMapping("/check")
    public ResponseEntity<AvailabilityCheckResponse> checkAvailability(
            @PathVariable Long guideId,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime requestedStart,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime requestedEnd) {

        boolean available = availabilityManagementService.checkAvailability(
                guideId,
                requestedStart,
                requestedEnd
        );

        return ResponseEntity.ok(new AvailabilityCheckResponse(available));
    }
}