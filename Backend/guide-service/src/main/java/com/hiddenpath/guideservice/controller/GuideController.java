package com.hiddenpath.guideservice.controller;

import com.hiddenpath.guideservice.dto.GuideRequest;
import com.hiddenpath.guideservice.dto.GuideResponse;
import com.hiddenpath.guideservice.entity.Guide;
import com.hiddenpath.guideservice.mapper.GuideMapper;

import com.hiddenpath.guideservice.service.GuideManagementService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/guides")
public class GuideController {

    private final GuideManagementService guideManagementService;

    public GuideController(GuideManagementService guideManagementService) {
        this.guideManagementService = guideManagementService;
    }

    // Create a guide profile
    @PostMapping
    public ResponseEntity<GuideResponse> createGuide(
            @Valid @RequestBody GuideRequest request) {

        Guide guide = GuideMapper.toEntity(request);

        Guide createdGuide = guideManagementService.createGuide(guide);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(GuideMapper.toResponse(createdGuide));
    }

    // Retrieve a guide by ID
    @GetMapping("/{id}")
    public ResponseEntity<GuideResponse> getGuideById(
            @PathVariable Long id) {

        Guide guide = guideManagementService.getGuideById(id);

        return ResponseEntity.ok(GuideMapper.toResponse(guide));
    }

    // Retrieve all guides
    @GetMapping
    public ResponseEntity<List<GuideResponse>> getAllGuides() {

        List<GuideResponse> guides = guideManagementService
                .getAllGuides()
                .stream()
                .map(GuideMapper::toResponse)
                .toList();

        return ResponseEntity.ok(guides);
    }

    // Update a guide profile
    @PutMapping("/{id}")
    public ResponseEntity<GuideResponse> updateGuide(
            @PathVariable Long id,
            @Valid @RequestBody GuideRequest request) {

        Guide guide = GuideMapper.toEntity(request);

        Guide updatedGuide = guideManagementService.updateGuide(id, guide);

        return ResponseEntity.ok(GuideMapper.toResponse(updatedGuide));
    }
    // Delete a guide profile
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGuide(
            @PathVariable Long id) {

        guideManagementService.deleteGuide(id);

        return ResponseEntity.noContent().build();
    }
}