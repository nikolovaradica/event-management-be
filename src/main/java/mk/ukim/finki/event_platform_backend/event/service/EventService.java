package mk.ukim.finki.event_platform_backend.event.service;

import mk.ukim.finki.event_platform_backend.event.model.dto.EventRequest;
import mk.ukim.finki.event_platform_backend.event.model.dto.EventResponse;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    Page<EventResponse> findWithFilters(String search, Long categoryId, String city, LocalDateTime from,
                                        LocalDateTime to, int page, int size);
    EventResponse findById(Long id);
    EventResponse create(EventRequest request);
    EventResponse update(Long id, EventRequest request);
    void delete(Long id);
    List<String> getCities();
}
