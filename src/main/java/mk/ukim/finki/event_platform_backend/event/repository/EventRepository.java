package mk.ukim.finki.event_platform_backend.event.repository;

import mk.ukim.finki.event_platform_backend.event.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByCategoryId(Long categoryId);
    List<Event> findByCity(String city);
    List<Event> findByEventDateGreaterThanEqualAndEventDateLessThan(
            LocalDateTime from,
            LocalDateTime to
    );
    @Query("""
            SELECT e
            FROM Event e
            WHERE (:search IS NULL OR
                   LOWER(e.title) LIKE LOWER(CONCAT('%', CAST(:search AS string), '%'))
                   OR LOWER(e.description) LIKE LOWER(CONCAT('%', CAST(:search AS string), '%')))
              AND (:categoryId IS NULL OR e.category.id = :categoryId)
              AND (:city IS NULL OR LOWER(e.city) = LOWER(CAST(:city AS string)))
              AND e.eventDate >= :from
              AND e.eventDate <= :to
            """)
    Page<Event> findWithFilters(@Param("search") String search,
                                @Param("categoryId") Long categoryId,
                                @Param("city") String city,
                                @Param("from") LocalDateTime from,
                                @Param("to") LocalDateTime to,
                                Pageable pageable);
    @Query("""
        SELECT DISTINCT e.city
        FROM Event e
        ORDER BY e.city
    """)
    List<String> findDistinctCities();
}
