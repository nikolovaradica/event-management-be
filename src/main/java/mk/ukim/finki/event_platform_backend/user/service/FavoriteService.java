package mk.ukim.finki.event_platform_backend.user.service;

import mk.ukim.finki.event_platform_backend.user.model.dto.FavoriteResponse;

import java.util.List;

public interface FavoriteService {
    FavoriteResponse addFavorite(Long eventId);
    void removeFavorite(Long eventId);
    List<FavoriteResponse> getFavorites();
    boolean isFavorite(Long eventId);
}
