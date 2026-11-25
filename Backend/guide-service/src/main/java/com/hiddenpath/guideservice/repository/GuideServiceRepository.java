
package com.hiddenpath.guideservice.repository;

import com.hiddenpath.guideservice.entity.GuideService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuideServiceRepository
        extends JpaRepository<GuideService, Long> {

    List<GuideService> findByGuideId(Long guideId);

}
