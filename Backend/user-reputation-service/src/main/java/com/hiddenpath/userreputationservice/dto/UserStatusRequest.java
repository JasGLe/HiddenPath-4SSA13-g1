package com.hiddenpath.userreputationservice.dto;

import com.hiddenpath.userreputationservice.entity.enums.UserStatus;
import jakarta.validation.constraints.NotNull;

public record UserStatusRequest(@NotNull UserStatus status) {}
