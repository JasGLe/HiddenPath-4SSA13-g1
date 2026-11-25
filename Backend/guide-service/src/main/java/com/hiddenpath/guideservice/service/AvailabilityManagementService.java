
package com.hiddenpath.guideservice.service;

import com.hiddenpath.guideservice.entity.AvailabilityCalendar;
import com.hiddenpath.guideservice.entity.AvailabilitySlot;
import com.hiddenpath.guideservice.entity.Guide;
import com.hiddenpath.guideservice.entity.UnavailabilityPeriod;
import com.hiddenpath.guideservice.enums.SlotStatus;
import com.hiddenpath.guideservice.repository.AvailabilitySlotRepository;
import com.hiddenpath.guideservice.repository.GuideRepository;
import com.hiddenpath.guideservice.repository.UnavailabilityPeriodRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AvailabilityManagementService {

    private final GuideRepository guideRepository;
    private final AvailabilitySlotRepository availabilitySlotRepository;
    private final UnavailabilityPeriodRepository unavailabilityPeriodRepository;

    // Constructor injection
    public AvailabilityManagementService(
            GuideRepository guideRepository,
            AvailabilitySlotRepository availabilitySlotRepository,
            UnavailabilityPeriodRepository unavailabilityPeriodRepository) {

        this.guideRepository = guideRepository;
        this.availabilitySlotRepository = availabilitySlotRepository;
        this.unavailabilityPeriodRepository = unavailabilityPeriodRepository;
    }

    // 1. Add an availability slot to a guide
    public AvailabilitySlot addAvailabilitySlot(
            Long guideId,
            AvailabilitySlot slot) {

        if (slot == null
                || slot.getStartDateTime() == null
                || slot.getEndDateTime() == null
                || slot.getStatus() == null) {

            throw new IllegalArgumentException(
                    "Slot start, end and status are required"
            );
        }

        validatePeriod(slot.getStartDateTime(), slot.getEndDateTime());

        Guide guide = guideRepository.findById(guideId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Guide not found with ID: " + guideId
                        )
                );

        AvailabilityCalendar calendar = guide.getAvailabilityCalendar();

        if (calendar == null) {
            throw new IllegalStateException(
                    "This guide does not have an availability calendar"
            );
        }

        slot.setCalendar(calendar);

        return availabilitySlotRepository.save(slot);
    }

    // 2. Check whether a guide is available during a requested period
    @Transactional(readOnly = true)
    public boolean checkAvailability(
            Long guideId,
            LocalDateTime requestedStart,
            LocalDateTime requestedEnd) {

        validatePeriod(requestedStart, requestedEnd);

        Guide guide = guideRepository.findById(guideId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Guide not found with ID: " + guideId
                        )
                );

        AvailabilityCalendar calendar = guide.getAvailabilityCalendar();

        if (calendar == null) {
            return false;
        }

        // Find an AVAILABLE slot covering the requested period
        boolean hasAvailableSlot = calendar.getSlots().stream()
                .anyMatch(slot ->
                        slot.getStatus() == SlotStatus.AVAILABLE
                                && !slot.getStartDateTime().isAfter(requestedStart)
                                && !slot.getEndDateTime().isBefore(requestedEnd)
                );

        if (!hasAvailableSlot) {
            return false;
        }

        // Check whether the requested period overlaps an unavailability period
        List<UnavailabilityPeriod> unavailablePeriods =
                unavailabilityPeriodRepository.findByGuideId(guideId);

        boolean hasConflict = unavailablePeriods.stream()
                .anyMatch(period ->
                        period.getStartDateTime().isBefore(requestedEnd)
                                && period.getEndDateTime().isAfter(requestedStart)
                );

        return !hasConflict;
    }

    // Validate date and time boundaries
    private void validatePeriod(
            LocalDateTime start,
            LocalDateTime end) {

        if (start == null || end == null) {
            throw new IllegalArgumentException(
                    "Start and end date/time are required"
            );
        }

        if (!end.isAfter(start)) {
            throw new IllegalArgumentException(
                    "End date/time must be after start date/time"
            );
        }
    }
}
