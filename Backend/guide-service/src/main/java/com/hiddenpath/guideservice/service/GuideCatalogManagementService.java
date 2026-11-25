
package com.hiddenpath.guideservice.service;

import com.hiddenpath.guideservice.entity.Guide;
import com.hiddenpath.guideservice.entity.Language;
import com.hiddenpath.guideservice.entity.GuideService;
import com.hiddenpath.guideservice.repository.GuideRepository;
import com.hiddenpath.guideservice.repository.LanguageRepository;
import com.hiddenpath.guideservice.repository.GuideServiceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GuideCatalogManagementService {

    private final GuideRepository guideRepository;
    private final LanguageRepository languageRepository;
    private final GuideServiceRepository guideServiceRepository;

    // Constructor injection
    public GuideCatalogManagementService(
            GuideRepository guideRepository,
            LanguageRepository languageRepository,
            GuideServiceRepository guideServiceRepository) {

        this.guideRepository = guideRepository;
        this.languageRepository = languageRepository;
        this.guideServiceRepository = guideServiceRepository;
    }

    // 1. Add a language to a guide
    public Guide addLanguageToGuide(Long guideId, Long languageId) {

        Guide guide = guideRepository.findById(guideId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Guide not found with ID: " + guideId
                        )
                );

        Language language = languageRepository.findById(languageId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Language not found with ID: " + languageId
                        )
                );

        if (!guide.getLanguages().contains(language)) {
            guide.getLanguages().add(language);
        }

        return guideRepository.save(guide);
    }

    // 2. Retrieve the languages of a guide
    @Transactional(readOnly = true)
    public List<Language> getGuideLanguages(Long guideId) {

        Guide guide = guideRepository.findById(guideId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Guide not found with ID: " + guideId
                        )
                );

        return guide.getLanguages();
    }

    // 3. Add a service or specialty to a guide
    public GuideService addServiceToGuide(
            Long guideId,
            String prestationSpecialite) {

        if (prestationSpecialite == null
                || prestationSpecialite.isBlank()) {
            throw new IllegalArgumentException(
                    "Service or specialty is required"
            );
        }

        Guide guide = guideRepository.findById(guideId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Guide not found with ID: " + guideId
                        )
                );

        GuideService guideService = new GuideService();
        guideService.setPrestationSpecialite(prestationSpecialite);
        guideService.setGuide(guide);

        return guideServiceRepository.save(guideService);
    }

    // 4. Retrieve all services offered by a guide
    @Transactional(readOnly = true)
    public List<GuideService> getGuideServices(Long guideId) {

        if (!guideRepository.existsById(guideId)) {
            throw new EntityNotFoundException(
                    "Guide not found with ID: " + guideId
            );
        }

        return guideServiceRepository.findByGuideId(guideId);
    }
}
