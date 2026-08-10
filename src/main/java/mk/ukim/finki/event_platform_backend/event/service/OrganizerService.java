package mk.ukim.finki.event_platform_backend.event.service;

import mk.ukim.finki.event_platform_backend.event.model.dto.OrganizerRequest;
import mk.ukim.finki.event_platform_backend.event.model.dto.OrganizerResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrganizerService {
    Page<OrganizerResponse> findAll(int page, int size, String search);
    OrganizerResponse findById(Long id);
    OrganizerResponse create(OrganizerRequest request);
    OrganizerResponse update(Long id, OrganizerRequest request);
    void delete(Long id);
}
