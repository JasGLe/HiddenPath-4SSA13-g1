package com.hiddenpath.userreputationservice.service;
import java.util.UUID;
import java.time.Instant;
import com.hiddenpath.userreputationservice.dto.PageResponse;
import com.hiddenpath.userreputationservice.dto.ReportCreateRequest;
import com.hiddenpath.userreputationservice.dto.ReportResponse;
import com.hiddenpath.userreputationservice.dto.ReportUpdateRequest;
import com.hiddenpath.userreputationservice.entity.Report;
import com.hiddenpath.userreputationservice.entity.User;
import com.hiddenpath.userreputationservice.entity.enums.ReportStatus;
import com.hiddenpath.userreputationservice.exception.BadRequestException;
import com.hiddenpath.userreputationservice.exception.ConflictException;
import com.hiddenpath.userreputationservice.exception.ResourceNotFoundException;
import com.hiddenpath.userreputationservice.repository.ReportRepository;
import com.hiddenpath.userreputationservice.repository.ReviewRepository;
import com.hiddenpath.userreputationservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Transactional
public class ReportService {

    private final ReportRepository reportRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public ReportResponse create(Jwt jwt, ReportCreateRequest req) {
        User reporter = userService.requireCurrentUser(jwt);

        switch (req.targetType()) {
            case USER -> {
                if (reporter.getId().equals(req.targetId())) {
                    throw new BadRequestException("You cannot report yourself");
                }
                if (!userRepository.existsById(req.targetId())) {
                    throw new ResourceNotFoundException("User not found: " + req.targetId());
                }
            }
            case REVIEW -> {
                if (!reviewRepository.existsById(req.targetId())) {
                    throw new ResourceNotFoundException("Review not found: " + req.targetId());
                }
            }
        }

        if (reportRepository.existsByReporterIdAndTargetTypeAndTargetIdAndStatus(
                reporter.getId(), req.targetType(), req.targetId(), ReportStatus.PENDING)) {
            throw new ConflictException("You already have a pending report on this target");
        }

        Report report = Report.builder()
                .reporter(reporter)
                .targetType(req.targetType())
                .targetId(req.targetId())
                .reason(req.reason())
                .description(req.description())
                .build();
        return ReportResponse.from(reportRepository.save(report));
    }

    @Transactional(readOnly = true)
    public PageResponse<ReportResponse> list(ReportStatus status, Pageable pageable) {
        Page<Report> page = (status == null)
                ? reportRepository.findAll(pageable)
                : reportRepository.findByStatus(status, pageable);
        return PageResponse.from(page.map(ReportResponse::from));
    }

    public ReportResponse update(UUID id, ReportUpdateRequest req) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Report not found: " + id));
        report.setStatus(req.status());
        if (req.adminNote() != null) report.setAdminNote(req.adminNote());
        boolean closed = req.status() == ReportStatus.RESOLVED || req.status() == ReportStatus.REJECTED;
        report.setResolvedAt(closed ? Instant.now() : null);
        return ReportResponse.from(report);
    }
}