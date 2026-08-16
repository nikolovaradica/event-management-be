package mk.ukim.finki.event_platform_backend.notification.repository;

import mk.ukim.finki.event_platform_backend.notification.model.PushSubscription;
import mk.ukim.finki.event_platform_backend.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PushSubscriptionRepository extends JpaRepository<PushSubscription, Long> {
    Optional<PushSubscription> findByEndpoint(String endpoint);
    void deleteByEndpoint(String endpoint);
    List<PushSubscription> findByUser(User user);
}