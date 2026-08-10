package mk.ukim.finki.event_platform_backend.review.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mk.ukim.finki.event_platform_backend.review.model.dto.ReviewRequest;
import mk.ukim.finki.event_platform_backend.review.model.dto.ReviewResponse;
import mk.ukim.finki.event_platform_backend.review.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/events/{eventId}/reviews")
    public ResponseEntity<ReviewResponse> create(@PathVariable Long eventId,
                                                 @Valid @RequestBody ReviewRequest request) {
        return ResponseEntity.ok(reviewService.create(eventId, request));
    }

    @GetMapping("/events/{eventId}/reviews")
    public ResponseEntity<List<ReviewResponse>> findByEvent(@PathVariable Long eventId) {
        return ResponseEntity.ok(reviewService.findByEvent(eventId));
    }
}
