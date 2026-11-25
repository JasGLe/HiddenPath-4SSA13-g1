package com.hiddenpath.userreputationservice.repository;

import com.hiddenpath.userreputationservice.entity.Report;
import com.hiddenpath.userreputationservice.entity.Review;
import com.hiddenpath.userreputationservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);
    boolean existsByKeycloakId(String keycloakId);
    Optional<User> findByKeycloakId(String keycloakId);

}