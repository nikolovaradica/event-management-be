package mk.ukim.finki.event_platform_backend.user.service.impl;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.event_platform_backend.common.exception.AlreadyExistsException;
import mk.ukim.finki.event_platform_backend.common.exception.NotFoundException;
import mk.ukim.finki.event_platform_backend.event.model.Event;
import mk.ukim.finki.event_platform_backend.event.repository.EventRepository;
import mk.ukim.finki.event_platform_backend.security.SecurityUtils;
import mk.ukim.finki.event_platform_backend.user.model.Favorite;
import mk.ukim.finki.event_platform_backend.user.model.User;
import mk.ukim.finki.event_platform_backend.user.model.dto.FavoriteResponse;
import mk.ukim.finki.event_platform_backend.user.repository.FavoriteRepository;
import mk.ukim.finki.event_platform_backend.user.service.FavoriteService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final EventRepository eventRepository;
    private final SecurityUtils securityUtils;

    @Override
    public FavoriteResponse addFavorite(Long eventId) {
        User user = securityUtils.getCurrentUser();
        favoriteRepository
                .findByUserIdAndEventId(user.getId(), eventId)
                .ifPresent(favorite -> {throw new AlreadyExistsException("Event already in favorites");});
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found"));
        Favorite favorite = Favorite.builder()
                .user(user)
                .event(event)
                .build();
        return mapToResponse(favoriteRepository.save(favorite));
    }

    @Override
    public void removeFavorite(Long eventId) {
        User user = securityUtils.getCurrentUser();
        Favorite favorite = favoriteRepository
                .findByUserIdAndEventId(user.getId(), eventId)
                .orElseThrow(() -> new NotFoundException("Favorite not found"));
        favoriteRepository.delete(favorite);
    }

    @Override
    public List<FavoriteResponse> getFavorites() {
        User user = securityUtils.getCurrentUser();
        return favoriteRepository.findByUserId(user.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public boolean isFavorite(Long eventId) {
        User user = securityUtils.getCurrentUser();
        return favoriteRepository.existsByUserIdAndEventId(user.getId(), eventId);
    }

    private FavoriteResponse mapToResponse(Favorite favorite) {
        return FavoriteResponse.builder()
                .id(favorite.getId())
                .eventId(favorite.getEvent().getId())
                .eventTitle(favorite.getEvent().getTitle())
                .eventDate(favorite.getEvent().getEventDate())
                .city(favorite.getEvent().getCity())
                .venue(favorite.getEvent().getVenue())
                .imageUrl(favorite.getEvent().getImageUrl())
                .categoryName(favorite.getEvent().getCategory().getName())
                .organizerName(favorite.getEvent().getOrganizer().getName())
                .build();
    }
}
