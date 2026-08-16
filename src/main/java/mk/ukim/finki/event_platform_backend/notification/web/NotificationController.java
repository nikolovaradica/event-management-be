package mk.ukim.finki.event_platform_backend.notification.web;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.event_platform_backend.notification.model.dto.PushSubscriptionRequest;
import mk.ukim.finki.event_platform_backend.notification.service.FavoriteEventNotificationService;
import mk.ukim.finki.event_platform_backend.notification.service.PushSubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final PushSubscriptionService pushSubscriptionService;
    private final FavoriteEventNotificationService notificationService;

    @PostMapping("/subscriptions")
    public ResponseEntity<Void> subscribe(@RequestBody PushSubscriptionRequest request) {
        pushSubscriptionService.subscribe(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/subscriptions")
    public ResponseEntity<Void> unsubscribe(@RequestBody PushSubscriptionRequest request) {
        pushSubscriptionService.unsubscribe(request.getEndpoint());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/test/favorite-event-reminder")
    public ResponseEntity<Void> testFavoriteEventReminder() {
        notificationService.sendFavoriteEventReminders();
        return ResponseEntity.ok().build();
    }
}