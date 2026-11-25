package com.hiddenpath.userreputationservice.controller;

import com.hiddenpath.userreputationservice.dto.UserCreateRequest;
import com.hiddenpath.userreputationservice.dto.UserResponse;
import com.hiddenpath.userreputationservice.dto.UserStatusRequest;
import com.hiddenpath.userreputationservice.dto.UserUpdateRequest;
import com.hiddenpath.userreputationservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> create(@AuthenticationPrincipal Jwt jwt,
                                               @Valid @RequestBody(required = false) UserCreateRequest req) {
        UserResponse created = userService.create(jwt, req);
        return ResponseEntity.created(URI.create("/api/users/" + created.id())).body(created);
    }

    @GetMapping("/me")
    public UserResponse me(@AuthenticationPrincipal Jwt jwt) {
        return userService.getByKeycloakId(jwt.getSubject());
    }

    @PatchMapping("/me")
    public UserResponse updateMe(@AuthenticationPrincipal Jwt jwt,
                                 @Valid @RequestBody UserUpdateRequest req) {
        return userService.updateByKeycloakId(jwt.getSubject(), req);
    }

    @GetMapping("/{userId}")
    public UserResponse getPublicProfile(@PathVariable UUID userId) {
        return userService.getById(userId);
    }

    @PreAuthorize("hasRole('admin')")
    @PatchMapping("/{userId}")
    public UserResponse updateStatus(@PathVariable UUID userId,
                                     @Valid @RequestBody UserStatusRequest req) {
        return userService.updateStatus(userId, req.status());
    }
}