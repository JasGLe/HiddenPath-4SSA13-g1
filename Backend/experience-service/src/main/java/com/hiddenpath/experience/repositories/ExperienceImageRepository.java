package com.hiddenpath.experience.repositories;

import com.hiddenpath.experience.entities.ExperienceImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ExperienceImageRepository
        extends JpaRepository<ExperienceImage, UUID> {

    List<ExperienceImage> findAllByExperience_Id(UUID experienceId);
}