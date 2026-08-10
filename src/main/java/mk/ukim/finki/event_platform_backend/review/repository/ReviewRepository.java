package mk.ukim.finki.event_platform_backend.review.repository;

import mk.ukim.finki.event_platform_backend.review.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByEventId(Long eventId);
    List<Review> findByUserId(Long userId);
    boolean existsByUserIdAndEventId(Long userId, Long eventId);
}
