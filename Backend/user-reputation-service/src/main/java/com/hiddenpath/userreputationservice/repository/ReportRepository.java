package com.hiddenpath.userreputationservice.repository;

import com.hiddenpath.userreputationservice.entity.Report;
import com.hiddenpath.userreputationservice.entity.enums.ReportStatus;
import com.hiddenpath.userreputationservice.entity.enums.ReportTargetType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReportRepository extends JpaRepository<Report, UUID> {

    Page<Report> findByStatus(ReportStatus status, Pageable pageable);

    boolean existsByReporterIdAndTargetTypeAndTargetIdAndStatus(
            UUID reporterId, ReportTargetType targetType, UUID targetId, ReportStatus status);
}