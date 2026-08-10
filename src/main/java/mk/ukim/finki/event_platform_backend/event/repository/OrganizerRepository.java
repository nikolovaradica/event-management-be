package mk.ukim.finki.event_platform_backend.event.repository;

import mk.ukim.finki.event_platform_backend.event.model.Organizer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizerRepository extends JpaRepository<Organizer, Long> {
    Page<Organizer> findByNameContainingIgnoreCase(String search, Pageable pageable);
}
