
package com.hiddenpath.guideservice.repository;

import com.hiddenpath.guideservice.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LanguageRepository
        extends JpaRepository<Language, Long> {

    Optional<Language> findByLangueIgnoreCase(String langue);

}
