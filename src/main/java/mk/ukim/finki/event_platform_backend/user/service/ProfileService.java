package mk.ukim.finki.event_platform_backend.user.service;

import mk.ukim.finki.event_platform_backend.user.model.dto.ProfileResponse;
import mk.ukim.finki.event_platform_backend.user.model.dto.UserReviewResponse;

import java.util.List;

public interface ProfileService {
    ProfileResponse getProfile();
    List<UserReviewResponse> getReviews();
}
