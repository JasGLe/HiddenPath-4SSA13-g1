
package com.hiddenpath.guideservice.repository;

import com.hiddenpath.guideservice.entity.AvailabilitySlot;
import com.hiddenpath.guideservice.enums.SlotStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvailabilitySlotRepository
        extends JpaRepository<AvailabilitySlot, Long> {

    List<AvailabilitySlot> findByCalendarGuideId(Long guideId);

    List<AvailabilitySlot> findByStatus(SlotStatus status);
}
