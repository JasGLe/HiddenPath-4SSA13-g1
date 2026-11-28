package com.hiddenpath.userreputationservice.repository;

import com.hiddenpath.userreputationservice.entity.Review;
import feign.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID>, JpaSpecificationExecutor<Review> {

    boolean existsByAuthorIdAndExperienceId(UUID authorId, UUID experienceId);

    @EntityGraph(attributePaths = "author")
    Page<Review> findAll(Specification<Review> spec, Pageable pageable);

    @Query("select avg(r.rating) as average, count(r) as total from Review r where r.guideId = :guideId")
    ReputationStats computeStats(@Param("guideId") UUID guideId);

    @Query("select r.rating as rating, count(r) as total from Review r " +
            "where r.guideId = :guideId group by r.rating")
    List<RatingCount> countByRating(@Param("guideId") UUID guideId);

    interface ReputationStats {
        Double getAverage();
        Long getTotal();
    }

    interface RatingCount {
        Integer getRating();
        Long getTotal();
    }
}