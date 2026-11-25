
package com.hiddenpath.guideservice.service;

import com.hiddenpath.guideservice.entity.AvailabilityCalendar;
import com.hiddenpath.guideservice.entity.Guide;
import com.hiddenpath.guideservice.repository.AvailabilityCalendarRepository;
import com.hiddenpath.guideservice.repository.GuideRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AvailabilityCalendarManagementService {

    private final GuideRepository guideRepository;
    private final AvailabilityCalendarRepository calendarRepository;

    // Constructor injection
    public AvailabilityCalendarManagementService(
            GuideRepository guideRepository,
            AvailabilityCalendarRepository calendarRepository) {

        this.guideRepository = guideRepository;
        this.calendarRepository = calendarRepository;
    }

    // 1. Create an availability calendar for a guide
    public AvailabilityCalendar createCalendar(Long guideId) {

        Guide guide = guideRepository.findById(guideId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Guide not found with ID: " + guideId
                        )
                );

        if (calendarRepository.existsByGuideId(guideId)) {
            throw new IllegalArgumentException(
                    "This guide already has an availability calendar"
            );
        }

        AvailabilityCalendar calendar = new AvailabilityCalendar();

        calendar.setGuide(guide);

        return calendarRepository.save(calendar);
    }

    // 2. Retrieve a guide's availability calendar
    @Transactional(readOnly = true)
    public AvailabilityCalendar getCalendarByGuideId(Long guideId) {

        if (!guideRepository.existsById(guideId)) {
            throw new EntityNotFoundException(
                    "Guide not found with ID: " + guideId
            );
        }

        return calendarRepository.findByGuideId(guideId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Availability calendar not found"
                        )
                );
    }
}
