package mk.ukim.finki.event_platform_backend.user.web;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.event_platform_backend.user.model.dto.ProfileResponse;
import mk.ukim.finki.event_platform_backend.user.model.dto.UserReviewResponse;
import mk.ukim.finki.event_platform_backend.user.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<ProfileResponse> getProfile() {
        return ResponseEntity.ok(profileService.getProfile());
    }

    @GetMapping("/reviews")
    public ResponseEntity<List<UserReviewResponse>> getReviews() {
        return ResponseEntity.ok(profileService.getReviews());
    }
}
