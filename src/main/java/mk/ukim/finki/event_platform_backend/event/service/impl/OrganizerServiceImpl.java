package mk.ukim.finki.event_platform_backend.event.service.impl;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.event_platform_backend.common.exception.NotFoundException;
import mk.ukim.finki.event_platform_backend.event.model.Category;
import mk.ukim.finki.event_platform_backend.event.model.Organizer;
import mk.ukim.finki.event_platform_backend.event.model.dto.OrganizerRequest;
import mk.ukim.finki.event_platform_backend.event.model.dto.OrganizerResponse;
import mk.ukim.finki.event_platform_backend.event.repository.OrganizerRepository;
import mk.ukim.finki.event_platform_backend.event.service.OrganizerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizerServiceImpl implements OrganizerService {
    private final OrganizerRepository organizerRepository;

    @Override
    public Page<OrganizerResponse> findAll(int page, int size, String search) {
        page = Math.max(page, 0);
        if (size <= 0) {
            size = 10;
        }

        Sort sort = Sort.by("name").ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Organizer> organizers;

        if (search == null || search.isBlank()) {
            organizers = organizerRepository.findAll(pageable);
        } else {
            organizers = organizerRepository.findByNameContainingIgnoreCase(search, pageable);
        }

        return organizers.map(this::mapToResponse);
    }

    @Override
    public OrganizerResponse findById(Long id) {
        return mapToResponse(organizerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Organizer not found")));
    }

    @Override
    public OrganizerResponse create(OrganizerRequest request) {
        Organizer organizer = Organizer.builder()
                .name(request.getName())
                .description(request.getDescription())
                .website(request.getWebsite())
                .build();
        return mapToResponse(organizerRepository.save(organizer));
    }

    @Override
    public OrganizerResponse update(Long id, OrganizerRequest request) {
        Organizer organizer = organizerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Organizer not found"));
        organizer.setName(request.getName());
        organizer.setDescription(request.getDescription());
        organizer.setWebsite(request.getWebsite());
        return mapToResponse(organizerRepository.save(organizer));
    }

    @Override
    public void delete(Long id) {
        Organizer organizer = organizerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Organizer not found"));

        organizerRepository.delete(organizer);
    }

    private OrganizerResponse mapToResponse(Organizer organizer) {
        return OrganizerResponse.builder()
                .id(organizer.getId())
                .name(organizer.getName())
                .description(organizer.getDescription())
                .website(organizer.getWebsite())
                .build();
    }
}
