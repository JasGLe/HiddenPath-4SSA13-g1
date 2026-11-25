package com.hiddenpath.guideservice.mapper;

import com.hiddenpath.guideservice.dto.GuideRequest;
import com.hiddenpath.guideservice.dto.GuideResponse;
import com.hiddenpath.guideservice.entity.Guide;

public class GuideMapper {

    private GuideMapper() {
        // Utility class
    }

    public static Guide toEntity(GuideRequest request) {
        Guide guide = new Guide();

        guide.setUserId(request.getUserId());
        guide.setProfil(request.getProfil());

        return guide;
    }

    public static GuideResponse toResponse(Guide guide) {
        return new GuideResponse(
                guide.getId(),
                guide.getUserId(),
                guide.getProfil()
        );
    }
}