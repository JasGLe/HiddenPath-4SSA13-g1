package com.hiddenpath.experience.services;

import com.hiddenpath.experience.dto.ExperienceCreateRequest;
import com.hiddenpath.experience.dto.ExperienceResponse;
import com.hiddenpath.experience.entities.Category;
import com.hiddenpath.experience.entities.Experience;
import com.hiddenpath.experience.enums.ExperienceStatus;
import com.hiddenpath.experience.repositories.CategoryRepository;
import com.hiddenpath.experience.repositories.ExperienceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final CategoryRepository categoryRepository;

    public ExperienceService(
            ExperienceRepository experienceRepository,
            CategoryRepository categoryRepository
    ) {
        this.experienceRepository = experienceRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public ExperienceResponse create(ExperienceCreateRequest request) {
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Categorie introuvable."
                ));

        Experience experience = new Experience();
        experience.setGuideId(request.guideId());
        experience.setTitle(request.title());
        experience.setDescription(request.description());
        experience.setPrice(request.price());
        experience.setDurationMinutes(request.durationMinutes());
        experience.setMaxParticipants(request.maxParticipants());
        experience.setCity(request.city());
        experience.setStatus(ExperienceStatus.DRAFT);
        experience.setCategory(category);

        Experience saved = experienceRepository.save(experience);

        return new ExperienceResponse(
                saved.getId(),
                saved.getGuideId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.getPrice(),
                saved.getDurationMinutes(),
                saved.getMaxParticipants(),
                saved.getCity(),
                saved.getStatus(),
                saved.getCategory().getId()
        );
    }

    @Transactional(readOnly = true)
    public List<ExperienceResponse> findAll() {
        return experienceRepository.findAll()
                .stream()
                .map(experience -> new ExperienceResponse(
                        experience.getId(),
                        experience.getGuideId(),
                        experience.getTitle(),
                        experience.getDescription(),
                        experience.getPrice(),
                        experience.getDurationMinutes(),
                        experience.getMaxParticipants(),
                        experience.getCity(),
                        experience.getStatus(),
                        experience.getCategory().getId()
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public ExperienceResponse findById(java.util.UUID id) {
        Experience experience = experienceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Experience introuvable."
                ));

        return new ExperienceResponse(
                experience.getId(),
                experience.getGuideId(),
                experience.getTitle(),
                experience.getDescription(),
                experience.getPrice(),
                experience.getDurationMinutes(),
                experience.getMaxParticipants(),
                experience.getCity(),
                experience.getStatus(),
                experience.getCategory().getId()
        );
    }

    @Transactional
    public ExperienceResponse update(
            java.util.UUID id,
            ExperienceCreateRequest request
    ) {
        Experience experience = experienceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Experience introuvable."
                ));

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Categorie introuvable."
                ));

        experience.setGuideId(request.guideId());
        experience.setTitle(request.title());
        experience.setDescription(request.description());
        experience.setPrice(request.price());
        experience.setDurationMinutes(request.durationMinutes());
        experience.setMaxParticipants(request.maxParticipants());
        experience.setCity(request.city());
        experience.setCategory(category);

        Experience saved = experienceRepository.save(experience);

        return new ExperienceResponse(
                saved.getId(),
                saved.getGuideId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.getPrice(),
                saved.getDurationMinutes(),
                saved.getMaxParticipants(),
                saved.getCity(),
                saved.getStatus(),
                saved.getCategory().getId()
        );
    }

    @Transactional
    public void delete(java.util.UUID id) {
        Experience experience = experienceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Experience introuvable."
                ));

        experienceRepository.delete(experience);
    }
}
