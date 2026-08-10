package mk.ukim.finki.event_platform_backend.review.service.impl;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.event_platform_backend.common.exception.AlreadyExistsException;
import mk.ukim.finki.event_platform_backend.common.exception.NotFoundException;
import mk.ukim.finki.event_platform_backend.event.model.Event;
import mk.ukim.finki.event_platform_backend.event.repository.EventRepository;
import mk.ukim.finki.event_platform_backend.review.model.Review;
import mk.ukim.finki.event_platform_backend.review.model.dto.ReviewRequest;
import mk.ukim.finki.event_platform_backend.review.model.dto.ReviewResponse;
import mk.ukim.finki.event_platform_backend.review.repository.ReviewRepository;
import mk.ukim.finki.event_platform_backend.review.service.ReviewService;
import mk.ukim.finki.event_platform_backend.security.SecurityUtils;
import mk.ukim.finki.event_platform_backend.user.model.User;
import mk.ukim.finki.event_platform_backend.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final SecurityUtils securityUtils;

    @Override
    public ReviewResponse create(Long eventId, ReviewRequest request) {
        User user = securityUtils.getCurrentUser();
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found"));

        if (reviewRepository.existsByUserIdAndEventId(user.getId(), eventId)) {
            throw new AlreadyExistsException("You have already reviewed this event.");
        }

        Review review = Review.builder()
                .rating(request.getRating())
                .comment(request.getComment())
                .createdAt(LocalDateTime.now())
                .event(event)
                .user(user)
                .build();
        return mapToResponse(reviewRepository.save(review));
    }

    @Override
    public List<ReviewResponse> findByEvent(Long eventId) {
        eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found"));
        return reviewRepository.findByEventId(eventId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private ReviewResponse mapToResponse(Review review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .rating(review.getRating())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .userId(review.getUser().getId())
                .username(review.getUser().getUsername())
                .build();
    }
}