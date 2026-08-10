package mk.ukim.finki.event_platform_backend.event.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mk.ukim.finki.event_platform_backend.event.model.dto.EventRequest;
import mk.ukim.finki.event_platform_backend.event.model.dto.EventResponse;
import mk.ukim.finki.event_platform_backend.event.service.EventService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @GetMapping
    public ResponseEntity<Page<EventResponse>> findAll(@RequestParam(required = false) String search,
                                                       @RequestParam(required = false) Long category,
                                                       @RequestParam(required = false) String city,
                                                       @RequestParam(required = false) LocalDateTime from,
                                                       @RequestParam(required = false) LocalDateTime to,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "12") int size) {
        return ResponseEntity.ok(eventService.findWithFilters(search, category, city, from, to, page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.findById(id));
    }

    @PostMapping
    public ResponseEntity<EventResponse> create(@Valid @RequestBody EventRequest request) {
        return ResponseEntity.ok(eventService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventResponse> update(@PathVariable Long id,
                                                @Valid @RequestBody EventRequest request) {
        return ResponseEntity.ok(eventService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        eventService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cities")
    public ResponseEntity<List<String>> getCities() {
        return ResponseEntity.ok(eventService.getCities());
    }
}
