package com.hiddenpath.experience.controllers;

import com.hiddenpath.experience.dto.ExperienceCreateRequest;
import com.hiddenpath.experience.dto.ExperienceResponse;
import com.hiddenpath.experience.services.ExperienceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/experiences")
public class ExperienceController {

    private final ExperienceService experienceService;

    public ExperienceController(ExperienceService experienceService) {
        this.experienceService = experienceService;
    }

    @PostMapping
    public ResponseEntity<ExperienceResponse> create(
            @Valid @RequestBody ExperienceCreateRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(experienceService.create(request));
    }

    @GetMapping
    public List<ExperienceResponse> findAll() {
        return experienceService.findAll();
    }

    @GetMapping("/{id}")
    public ExperienceResponse findById(@PathVariable("id") UUID id) {
        return experienceService.findById(id);
    }

    @PutMapping("/{id}")
    public ExperienceResponse update(
            @PathVariable("id") UUID id,
            @Valid @RequestBody ExperienceCreateRequest request
    ) {
        return experienceService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        experienceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
