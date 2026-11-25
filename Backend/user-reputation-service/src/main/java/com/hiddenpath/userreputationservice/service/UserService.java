package com.hiddenpath.userreputationservice.service;

import com.hiddenpath.userreputationservice.dto.UserCreateRequest;
import com.hiddenpath.userreputationservice.dto.UserResponse;
import com.hiddenpath.userreputationservice.dto.UserUpdateRequest;
import com.hiddenpath.userreputationservice.entity.User;
import com.hiddenpath.userreputationservice.entity.enums.UserStatus;
import com.hiddenpath.userreputationservice.exception.ConflictException;
import com.hiddenpath.userreputationservice.exception.ResourceNotFoundException;
import com.hiddenpath.userreputationservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserResponse create(Jwt jwt, UserCreateRequest req) {
        String keycloakId = jwt.getSubject();
        String email = jwt.getClaimAsString("email");

        if (userRepository.existsByKeycloakId(keycloakId)) {
            throw new ConflictException("Profile already exists for this account");
        }
        if (email == null || userRepository.existsByEmail(email)) {
            throw new ConflictException("Email missing from token or already used");
        }

        User user = User.builder()
                .keycloakId(keycloakId)
                .email(email)
                .firstName(jwt.getClaimAsString("given_name"))
                .lastName(jwt.getClaimAsString("family_name"))
                .phone(req != null ? req.phone() : null)
                .country(req != null ? req.country() : null)
                .avatarUrl(req != null ? req.avatarUrl() : null)
                .bio(req != null ? req.bio() : null)
                .build();
        return UserResponse.from(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public UserResponse getByKeycloakId(String keycloakId) {
        return UserResponse.from(findByKeycloakId(keycloakId));
    }

    public UserResponse updateByKeycloakId(String keycloakId, UserUpdateRequest req) {
        return update(findByKeycloakId(keycloakId).getId(), req);
    }

    private User findByKeycloakId(String keycloakId) {
        return userRepository.findByKeycloakId(keycloakId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No profile for this account yet, call POST /api/users first"));
    }


    @Transactional(readOnly = true)
    public UserResponse getById(UUID id) {
        return UserResponse.from(find(id));
    }

    public UserResponse update(UUID id, UserUpdateRequest req) {
        User user = find(id);
        if (req.firstName() != null) user.setFirstName(req.firstName());
        if (req.lastName() != null)  user.setLastName(req.lastName());
        if (req.phone() != null)     user.setPhone(req.phone());
        if (req.country() != null)   user.setCountry(req.country());
        if (req.avatarUrl() != null) user.setAvatarUrl(req.avatarUrl());
        if (req.bio() != null)       user.setBio(req.bio());
        return UserResponse.from(user);   // dirty checking saves it
    }

    public UserResponse updateStatus(UUID id, UserStatus status) {
        User user = find(id);
        user.setStatus(status);
        return UserResponse.from(user);
    }

    private User find(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
    }
}