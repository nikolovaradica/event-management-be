package mk.ukim.finki.event_platform_backend.event.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mk.ukim.finki.event_platform_backend.event.model.dto.OrganizerRequest;
import mk.ukim.finki.event_platform_backend.event.model.dto.OrganizerResponse;
import mk.ukim.finki.event_platform_backend.event.service.OrganizerService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organizers")
@RequiredArgsConstructor
public class OrganizerController {
    private final OrganizerService organizerService;

    @GetMapping
    public ResponseEntity<Page<OrganizerResponse>> findAll(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size,
                                                           @RequestParam(required = false) String search) {
        return ResponseEntity.ok(organizerService.findAll(page, size, search));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizerResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(organizerService.findById(id));
    }

    @PostMapping
    public ResponseEntity<OrganizerResponse> create(@Valid @RequestBody OrganizerRequest request) {
        return ResponseEntity.ok(organizerService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrganizerResponse> update(@PathVariable Long id,
                                                    @Valid @RequestBody OrganizerRequest request) {
        return ResponseEntity.ok(organizerService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        organizerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
