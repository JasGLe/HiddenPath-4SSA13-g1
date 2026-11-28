package com.hiddenpath.userreputationservice.controller;

import com.hiddenpath.userreputationservice.dto.PageResponse;
import com.hiddenpath.userreputationservice.dto.ReviewCreateRequest;
import com.hiddenpath.userreputationservice.dto.ReviewResponse;
import com.hiddenpath.userreputationservice.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewResponse> create(@AuthenticationPrincipal Jwt jwt,
                                                 @Valid @RequestBody ReviewCreateRequest req) {
        ReviewResponse created = reviewService.create(jwt, req);
        return ResponseEntity.created(URI.create("/api/reviews/" + created.id())).body(created);
    }

    @GetMapping
    public PageResponse<ReviewResponse> list(
            @RequestParam(required = false) UUID experienceId,
            @RequestParam(required = false) UUID guideId,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return reviewService.search(experienceId, guideId, pageable);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id,
                                       @AuthenticationPrincipal Jwt jwt,
                                       Authentication authentication) {
        boolean admin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_admin"));
        reviewService.delete(id, jwt, admin);
        return ResponseEntity.noContent().build();
    }
}