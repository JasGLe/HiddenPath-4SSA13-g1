package com.hiddenpath.userreputationservice.controller;

import com.hiddenpath.userreputationservice.dto.ReputationResponse;
import com.hiddenpath.userreputationservice.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class ReputationController {

    private final ReviewService reviewService;

    @GetMapping("/{userId}/reputation")
    public ReputationResponse reputation(@PathVariable UUID userId) {
        return reviewService.reputation(userId);
    }
}