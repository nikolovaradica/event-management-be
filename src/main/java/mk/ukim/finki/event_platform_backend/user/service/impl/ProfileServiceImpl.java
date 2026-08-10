package mk.ukim.finki.event_platform_backend.user.service.impl;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.event_platform_backend.review.model.Review;
import mk.ukim.finki.event_platform_backend.review.repository.ReviewRepository;
import mk.ukim.finki.event_platform_backend.security.SecurityUtils;
import mk.ukim.finki.event_platform_backend.user.model.User;
import mk.ukim.finki.event_platform_backend.user.model.dto.ProfileResponse;
import mk.ukim.finki.event_platform_backend.user.model.dto.UserReviewResponse;
import mk.ukim.finki.event_platform_backend.user.repository.FavoriteRepository;
import mk.ukim.finki.event_platform_backend.user.service.ProfileService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final FavoriteRepository favoriteRepository;
    private final ReviewRepository reviewRepository;
    private final SecurityUtils securityUtils;

    @Override
    public ProfileResponse getProfile() {
        User user = securityUtils.getCurrentUser();
        long favoritesCount = favoriteRepository.findByUserId(user.getId()).size();
        long reviewsCount = reviewRepository.findByUserId(user.getId()).size();
        return mapToResponse(user, favoritesCount, reviewsCount);
    }

    @Override
    public List<UserReviewResponse> getReviews() {
        User user = securityUtils.getCurrentUser();
        List<Review> reviews = reviewRepository.findByUserId(user.getId());

        return reviews.stream()
                .map(this::mapToResponse)
                .toList();
    }

    private ProfileResponse mapToResponse(User user, long favoritesCount, long reviewsCount) {
        return ProfileResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .createdAt(user.getCreatedAt())
                .favoritesCount(favoritesCount)
                .reviewsCount(reviewsCount)
                .build();
    }

    private UserReviewResponse mapToResponse(Review review) {
        return UserReviewResponse.builder()
                .id(review.getId())
                .eventId(review.getEvent().getId())
                .eventTitle(review.getEvent().getTitle())
                .rating(review.getRating())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
