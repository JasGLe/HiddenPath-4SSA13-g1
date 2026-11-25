
package com.hiddenpath.guideservice.service;

import com.hiddenpath.guideservice.entity.Guide;
import com.hiddenpath.guideservice.entity.AvailabilityCalendar;
import com.hiddenpath.guideservice.repository.GuideRepository;
import com.hiddenpath.guideservice.repository.AvailabilityCalendarRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GuideManagementService {

    private final GuideRepository guideRepository;
    private final AvailabilityCalendarRepository calendarRepository;

    public GuideManagementService(
            GuideRepository guideRepository,
            AvailabilityCalendarRepository calendarRepository) {
        this.guideRepository = guideRepository;
        this.calendarRepository = calendarRepository;
    }

    // 1. Create a guide and its availability calendar
    public Guide createGuide(Guide guide) {

        if (guide == null || guide.getUserId() == null) {
            throw new IllegalArgumentException(
                    "Guide and user ID are required"
            );
        }

        if (guideRepository.existsByUserId(guide.getUserId())) {
            throw new IllegalArgumentException(
                    "A guide profile already exists for this user"
            );
        }

        Guide savedGuide = guideRepository.save(guide);

        AvailabilityCalendar calendar = new AvailabilityCalendar();
        calendar.setGuide(savedGuide);

        calendarRepository.save(calendar);

        return savedGuide;
    }

    // 2. Retrieve a guide by ID
    @Transactional(readOnly = true)
    public Guide getGuideById(Long id) {

        return guideRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Guide not found with ID: " + id
                        )
                );
    }

    // 3. Retrieve all guides
    @Transactional(readOnly = true)
    public List<Guide> getAllGuides() {

        return guideRepository.findAll();
    }

    // 4. Update a guide profile
    public Guide updateGuide(Long id, Guide updatedGuide) {

        if (updatedGuide == null) {
            throw new IllegalArgumentException(
                    "Updated guide data is required"
            );
        }

        Guide existingGuide = getGuideById(id);

        Long newUserId = updatedGuide.getUserId();

        if (newUserId == null) {
            throw new IllegalArgumentException(
                    "User ID is required"
            );
        }

        if (!existingGuide.getUserId().equals(newUserId)
                && guideRepository.existsByUserId(newUserId)) {
            throw new IllegalArgumentException(
                    "This user already has a guide profile"
            );
        }

        existingGuide.setUserId(newUserId);
        existingGuide.setProfil(updatedGuide.getProfil());

        return guideRepository.save(existingGuide);
    }
    // 5. Delete a guide profile
    public void deleteGuide(Long id) {

        Guide guide = guideRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Guide not found with ID: " + id
                        )
                );

        guideRepository.delete(guide);
    }
}
