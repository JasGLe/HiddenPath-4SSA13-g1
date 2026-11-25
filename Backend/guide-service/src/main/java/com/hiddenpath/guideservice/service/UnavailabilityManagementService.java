
package com.hiddenpath.guideservice.service;

import com.hiddenpath.guideservice.entity.Guide;
import com.hiddenpath.guideservice.entity.UnavailabilityPeriod;
import com.hiddenpath.guideservice.repository.GuideRepository;
import com.hiddenpath.guideservice.repository.UnavailabilityPeriodRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class UnavailabilityManagementService {

    private final GuideRepository guideRepository;
    private final UnavailabilityPeriodRepository
            unavailabilityPeriodRepository;

    // Constructor injection
    public UnavailabilityManagementService(
            GuideRepository guideRepository,
            UnavailabilityPeriodRepository unavailabilityPeriodRepository) {

        this.guideRepository = guideRepository;
        this.unavailabilityPeriodRepository =
                unavailabilityPeriodRepository;
    }

    // 1. Declare an unavailability period
    public UnavailabilityPeriod declareUnavailability(
            Long guideId,
            UnavailabilityPeriod period) {

        if (period == null
                || period.getStartDateTime() == null
                || period.getEndDateTime() == null) {

            throw new IllegalArgumentException(
                    "Unavailability start and end dates are required"
            );
        }

        validatePeriod(
                period.getStartDateTime(),
                period.getEndDateTime()
        );

        Guide guide = guideRepository.findById(guideId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Guide not found with ID: " + guideId
                        )
                );

        period.setGuide(guide);

        return unavailabilityPeriodRepository.save(period);
    }

    // 2. Retrieve all unavailability periods of a guide
    @Transactional(readOnly = true)
    public List<UnavailabilityPeriod> getGuideUnavailability(
            Long guideId) {

        if (!guideRepository.existsById(guideId)) {
            throw new EntityNotFoundException(
                    "Guide not found with ID: " + guideId
            );
        }

        return unavailabilityPeriodRepository.findByGuideId(guideId);
    }

    // Validate the period
    private void validatePeriod(
            LocalDateTime start,
            LocalDateTime end) {

        if (start == null || end == null) {
            throw new IllegalArgumentException(
                    "Start and end dates are required"
            );
        }

        if (!end.isAfter(start)) {
            throw new IllegalArgumentException(
                    "End date must be after start date"
            );
        }
    }
}
