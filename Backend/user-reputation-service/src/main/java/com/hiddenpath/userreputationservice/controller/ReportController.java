package com.hiddenpath.userreputationservice.controller;

import com.hiddenpath.userreputationservice.dto.PageResponse;
import com.hiddenpath.userreputationservice.dto.ReportCreateRequest;
import com.hiddenpath.userreputationservice.dto.ReportResponse;
import com.hiddenpath.userreputationservice.dto.ReportUpdateRequest;
import com.hiddenpath.userreputationservice.entity.enums.ReportStatus;
import com.hiddenpath.userreputationservice.service.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<ReportResponse> create(@AuthenticationPrincipal Jwt jwt,
                                                 @Valid @RequestBody ReportCreateRequest req) {
        ReportResponse created = reportService.create(jwt, req);
        return ResponseEntity.created(URI.create("/api/reports/" + created.id())).body(created);
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping
    public PageResponse<ReportResponse> list(
            @RequestParam(required = false) ReportStatus status,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return reportService.list(status, pageable);
    }

    @PreAuthorize("hasRole('admin')")
    @PatchMapping("/{id}")
    public ReportResponse update(@PathVariable UUID id, @Valid @RequestBody ReportUpdateRequest req) {
        return reportService.update(id, req);
    }
}