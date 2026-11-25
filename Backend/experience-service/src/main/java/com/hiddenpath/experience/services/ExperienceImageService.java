package com.hiddenpath.experience.services;

import com.hiddenpath.experience.dto.ExperienceImageCreateRequest;
import com.hiddenpath.experience.dto.ExperienceImageResponse;
import com.hiddenpath.experience.entities.Experience;
import com.hiddenpath.experience.entities.ExperienceImage;
import com.hiddenpath.experience.repositories.ExperienceImageRepository;
import com.hiddenpath.experience.repositories.ExperienceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class ExperienceImageService {

    private final ExperienceRepository experienceRepository;
    private final ExperienceImageRepository imageRepository;

    public ExperienceImageService(
            ExperienceRepository experienceRepository,
            ExperienceImageRepository imageRepository
    ) {
        this.experienceRepository = experienceRepository;
        this.imageRepository = imageRepository;
    }

    @Transactional
    public ExperienceImageResponse create(
            UUID experienceId,
            ExperienceImageCreateRequest request
    ) {
        Experience experience = findExperience(experienceId);

        ExperienceImage image = new ExperienceImage();
        image.setUrl(request.url().trim());
        image.setCover(request.cover());
        image.setExperience(experience);

        return toResponse(imageRepository.save(image));
    }

    @Transactional(readOnly = true)
    public List<ExperienceImageResponse> findAll(UUID experienceId) {
        findExperience(experienceId);

        return imageRepository.findAllByExperience_Id(experienceId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public void delete(UUID experienceId, UUID imageId) {
        findExperience(experienceId);

        ExperienceImage image = imageRepository.findById(imageId)
                .filter(item ->
                        item.getExperience().getId().equals(experienceId)
                )
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Image introuvable pour cette experience."
                ));

        imageRepository.delete(image);
    }

    private Experience findExperience(UUID experienceId) {
        return experienceRepository.findById(experienceId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Experience introuvable."
                ));
    }

    private ExperienceImageResponse toResponse(ExperienceImage image) {
        return new ExperienceImageResponse(
                image.getId(),
                image.getUrl(),
                image.isCover(),
                image.getExperience().getId()
        );
    }
}