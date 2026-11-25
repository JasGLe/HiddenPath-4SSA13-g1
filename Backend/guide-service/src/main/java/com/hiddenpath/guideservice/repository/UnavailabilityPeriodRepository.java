
package com.hiddenpath.guideservice.repository;

import com.hiddenpath.guideservice.entity.UnavailabilityPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnavailabilityPeriodRepository
        extends JpaRepository<UnavailabilityPeriod, Long> {

    List<UnavailabilityPeriod> findByGuideId(Long guideId);
}
