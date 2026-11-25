package com.hiddenpath.experience.controllers;

import com.hiddenpath.experience.dto.ExperienceImageCreateRequest;
import com.hiddenpath.experience.dto.ExperienceImageResponse;
import com.hiddenpath.experience.services.ExperienceImageService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/experiences/{experienceId}/images")
public class ExperienceImageController {

    private final ExperienceImageService imageService;

    public ExperienceImageController(ExperienceImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping
    public ResponseEntity<ExperienceImageResponse> create(
            @PathVariable("experienceId") UUID experienceId,
            @Valid @RequestBody ExperienceImageCreateRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(imageService.create(experienceId, request));
    }

    @GetMapping
    public List<ExperienceImageResponse> findAll(
            @PathVariable("experienceId") UUID experienceId
    ) {
        return imageService.findAll(experienceId);
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<Void> delete(
            @PathVariable("experienceId") UUID experienceId,
            @PathVariable("imageId") UUID imageId
    ) {
        imageService.delete(experienceId, imageId);
        return ResponseEntity.noContent().build();
    }
}