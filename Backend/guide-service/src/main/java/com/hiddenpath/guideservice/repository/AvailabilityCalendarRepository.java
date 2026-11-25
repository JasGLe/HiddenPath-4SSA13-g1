
package com.hiddenpath.guideservice.repository;

import com.hiddenpath.guideservice.entity.AvailabilityCalendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AvailabilityCalendarRepository
        extends JpaRepository<AvailabilityCalendar, Long> {

    // Find a calendar using its guide ID
    Optional<AvailabilityCalendar> findByGuideId(Long guideId);

    // Check whether a guide already has a calendar
    boolean existsByGuideId(Long guideId);
}
