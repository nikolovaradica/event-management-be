package mk.ukim.finki.event_platform_backend.notification.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mk.ukim.finki.event_platform_backend.notification.model.dto.PushSubscriptionRequest;
import mk.ukim.finki.event_platform_backend.notification.model.PushSubscription;
import mk.ukim.finki.event_platform_backend.notification.repository.PushSubscriptionRepository;
import mk.ukim.finki.event_platform_backend.security.SecurityUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PushSubscriptionService {
    private final PushSubscriptionRepository pushSubscriptionRepository;
    private final SecurityUtils securityUtils;

    public void subscribe(PushSubscriptionRequest request) {
        PushSubscription subscription = pushSubscriptionRepository
                .findByEndpoint(request.getEndpoint())
                .orElse(PushSubscription.builder()
                                .endpoint(request.getEndpoint())
                                .build()
                );

        subscription.setP256dh(request.getP256dh());
        subscription.setAuth(request.getAuth());
        subscription.setUser(securityUtils.getCurrentUser());

        pushSubscriptionRepository.save(subscription);
    }

    @Transactional
    public void unsubscribe(String endpoint) {
        pushSubscriptionRepository.deleteByEndpoint(endpoint);
    }
}
