package com.hiddenpath.userreputationservice.service;

import com.hiddenpath.userreputationservice.client.ParticipationVerifier;
import com.hiddenpath.userreputationservice.dto.PageResponse;
import com.hiddenpath.userreputationservice.dto.ReputationResponse;
import com.hiddenpath.userreputationservice.dto.ReviewCreateRequest;
import com.hiddenpath.userreputationservice.dto.ReviewResponse;
import com.hiddenpath.userreputationservice.entity.Review;
import com.hiddenpath.userreputationservice.entity.User;
import com.hiddenpath.userreputationservice.entity.enums.UserStatus;
import com.hiddenpath.userreputationservice.exception.BadRequestException;
import com.hiddenpath.userreputationservice.exception.ConflictException;
import com.hiddenpath.userreputationservice.exception.ResourceNotFoundException;
import com.hiddenpath.userreputationservice.repository.ReviewRepository;
import com.hiddenpath.userreputationservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import java.util.TreeMap;
import java.util.UUID;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final ParticipationVerifier participationVerifier;

    public ReviewResponse create(Jwt jwt, ReviewCreateRequest req) {
        User author = userService.requireCurrentUser(jwt);

        if (author.getStatus() != UserStatus.ACTIVE) {
            throw new AccessDeniedException("Your account cannot publish reviews");
        }
        if (author.getId().equals(req.guideId())) {
            throw new BadRequestException("You cannot review yourself");
        }
        if (!userRepository.existsById(req.guideId())) {
            throw new ResourceNotFoundException("Guide not found: " + req.guideId());
        }
        if (reviewRepository.existsByAuthorIdAndExperienceId(author.getId(), req.experienceId())) {
            throw new ConflictException("You already reviewed this experience");
        }
        if (!participationVerifier.hasParticipated(author.getId(), req.experienceId())) {
            throw new AccessDeniedException("Only participants can review this experience");
        }

        Review review = Review.builder()
                .author(author)
                .experienceId(req.experienceId())
                .guideId(req.guideId())
                .rating(req.rating())
                .comment(req.comment())
                .build();
        return ReviewResponse.from(reviewRepository.save(review));
    }

    @Transactional(readOnly = true)
    public PageResponse<ReviewResponse> search(UUID experienceId, UUID guideId, Pageable pageable) {
        Specification<Review> spec = (root, query, cb) -> cb.conjunction();
        if (experienceId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("experienceId"), experienceId));
        }
        if (guideId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("guideId"), guideId));
        }
        return PageResponse.from(reviewRepository.findAll(spec, pageable).map(ReviewResponse::from));
    }

    public void delete(UUID id, Jwt jwt, boolean admin) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found: " + id));
        if (!admin) {
            User me = userService.requireCurrentUser(jwt);
            if (!review.getAuthor().getId().equals(me.getId())) {
                throw new AccessDeniedException("You can only delete your own reviews");
            }
        }
        reviewRepository.delete(review);
    }

    @Transactional(readOnly = true)
    public ReputationResponse reputation(UUID guideId) {
        if (!userRepository.existsById(guideId)) {
            throw new ResourceNotFoundException("User not found: " + guideId);
        }
        ReviewRepository.ReputationStats stats = reviewRepository.computeStats(guideId);
        long total = stats.getTotal() == null ? 0 : stats.getTotal();
        double average = (total == 0 || stats.getAverage() == null)
                ? 0 : Math.round(stats.getAverage() * 100.0) / 100.0;

        Map<Integer, Long> distribution = new TreeMap<>();
        for (int i = 1; i <= 5; i++) distribution.put(i, 0L);
        reviewRepository.countByRating(guideId)
                .forEach(rc -> distribution.put(rc.getRating(), rc.getTotal()));

        return new ReputationResponse(guideId, average, total, distribution);
    }
}