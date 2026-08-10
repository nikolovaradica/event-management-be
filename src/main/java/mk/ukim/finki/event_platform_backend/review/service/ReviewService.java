package mk.ukim.finki.event_platform_backend.review.service;

import mk.ukim.finki.event_platform_backend.review.model.dto.ReviewRequest;
import mk.ukim.finki.event_platform_backend.review.model.dto.ReviewResponse;

import java.util.List;

public interface ReviewService {
    ReviewResponse create(Long eventId, ReviewRequest request);
    List<ReviewResponse> findByEvent(Long eventId);
}
