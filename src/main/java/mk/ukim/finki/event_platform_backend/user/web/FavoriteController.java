package mk.ukim.finki.event_platform_backend.user.web;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.event_platform_backend.user.model.dto.FavoriteResponse;
import mk.ukim.finki.event_platform_backend.user.service.FavoriteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favoriteService;

    @PostMapping("/{eventId}")
    public ResponseEntity<FavoriteResponse> addFavorite(@PathVariable Long eventId) {
        return ResponseEntity.ok(favoriteService.addFavorite(eventId));
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> removeFavorite(@PathVariable Long eventId) {
        favoriteService.removeFavorite(eventId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<FavoriteResponse>> getFavorites() {
        return ResponseEntity.ok(favoriteService.getFavorites());
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<Boolean> isFavorite(@PathVariable Long eventId) {
        return ResponseEntity.ok(favoriteService.isFavorite(eventId));
    }
}