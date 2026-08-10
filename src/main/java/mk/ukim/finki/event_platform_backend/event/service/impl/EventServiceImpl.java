package mk.ukim.finki.event_platform_backend.event.service.impl;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.event_platform_backend.common.exception.NotFoundException;
import mk.ukim.finki.event_platform_backend.event.model.Category;
import mk.ukim.finki.event_platform_backend.event.model.Event;
import mk.ukim.finki.event_platform_backend.event.model.Organizer;
import mk.ukim.finki.event_platform_backend.event.model.dto.CategoryResponse;
import mk.ukim.finki.event_platform_backend.event.model.dto.EventRequest;
import mk.ukim.finki.event_platform_backend.event.model.dto.EventResponse;
import mk.ukim.finki.event_platform_backend.event.model.dto.OrganizerResponse;
import mk.ukim.finki.event_platform_backend.event.repository.CategoryRepository;
import mk.ukim.finki.event_platform_backend.event.repository.EventRepository;
import mk.ukim.finki.event_platform_backend.event.repository.OrganizerRepository;
import mk.ukim.finki.event_platform_backend.event.service.EventService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final OrganizerRepository organizerRepository;

    @Override
    public Page<EventResponse> findWithFilters(String search, Long categoryId, String city, LocalDateTime from,
                                               LocalDateTime to, int page, int size) {
        if (from == null)
            from = LocalDateTime.of(1970, 1, 1, 0, 0);
        if (to == null)
            to = LocalDateTime.of(9999, 12, 31, 23, 59, 59);

        page = Math.max(page, 0);
        if (size <= 0) {
            size = 12;
        }
        Sort sort = Sort.by("eventDate").ascending().and(Sort.by("title").ascending());
        Pageable pageable = PageRequest.of(page, size,sort);
        return eventRepository.findWithFilters(search, categoryId, city, from, to, pageable)
                .map(this::mapToResponse);
    }

    @Override
    public EventResponse findById(Long id) {
        return mapToResponse(eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event not found")));
    }

    @Override
    public EventResponse create(EventRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found"));
        Organizer organizer = organizerRepository.findById(request.getOrganizerId())
                .orElseThrow(() -> new NotFoundException("Organizer not found"));

        Event event = Event.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .eventDate(request.getEventDate())
                .city(request.getCity())
                .venue(request.getVenue())
                .imageUrl(request.getImageUrl())
                .createdAt(LocalDateTime.now())
                .category(category)
                .organizer(organizer)
                .build();
        return mapToResponse(eventRepository.save(event));
    }

    @Override
    public EventResponse update(Long id, EventRequest request) {
        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event not found"));
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found"));
        Organizer organizer = organizerRepository.findById(request.getOrganizerId())
                .orElseThrow(() -> new NotFoundException("Organizer not found"));

        existingEvent.setTitle(request.getTitle());
        existingEvent.setDescription(request.getDescription());
        existingEvent.setEventDate(request.getEventDate());
        existingEvent.setCity(request.getCity());
        existingEvent.setVenue(request.getVenue());
        existingEvent.setImageUrl(request.getImageUrl());
        existingEvent.setCategory(category);
        existingEvent.setOrganizer(organizer);
        return mapToResponse(eventRepository.save(existingEvent));
    }

    @Override
    public void delete(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event not found"));

        eventRepository.delete(event);
    }

    @Override
    public List<String> getCities() {
        return eventRepository.findDistinctCities();
    }

    private EventResponse mapToResponse(Event event) {
        return EventResponse.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .city(event.getCity())
                .venue(event.getVenue())
                .imageUrl(event.getImageUrl())
                .createdAt(event.getCreatedAt())
                .category(CategoryResponse.builder()
                        .id(event.getCategory().getId())
                        .name(event.getCategory().getName())
                        .description(event.getCategory().getDescription())
                        .build()
                )
                .organizer(OrganizerResponse.builder()
                        .id(event.getOrganizer().getId())
                        .name(event.getOrganizer().getName())
                        .description(event.getOrganizer().getDescription())
                        .website(event.getOrganizer().getWebsite())
                        .build()
                )
                .build();
    }
}
