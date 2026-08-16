package mk.ukim.finki.event_platform_backend.notification.service;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.event_platform_backend.event.model.Event;
import mk.ukim.finki.event_platform_backend.event.repository.EventRepository;
import mk.ukim.finki.event_platform_backend.notification.model.PushSubscription;
import mk.ukim.finki.event_platform_backend.notification.repository.PushSubscriptionRepository;
import mk.ukim.finki.event_platform_backend.user.model.Favorite;
import mk.ukim.finki.event_platform_backend.user.repository.FavoriteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteEventNotificationService {
    private final EventRepository eventRepository;
    private final FavoriteRepository favoriteRepository;
    private final PushSubscriptionRepository pushSubscriptionRepository;
    private final PushNotificationService pushNotificationService;

    public void sendFavoriteEventReminders() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalDateTime from = tomorrow.atStartOfDay();
        LocalDateTime to = tomorrow.plusDays(1).atStartOfDay();

        List<Event> events = eventRepository
                .findByEventDateGreaterThanEqualAndEventDateLessThan(from, to);

        for (Event event : events) {
            List<Favorite> favorites = favoriteRepository.findByEventId(event.getId());
            for (Favorite favorite : favorites) {
                List<PushSubscription> subscriptions =
                        pushSubscriptionRepository.findByUser(favorite.getUser());
                for (PushSubscription subscription : subscriptions) {
                    sendNotification(event, subscription);
                }
            }
        }
    }

    private void sendNotification(Event event, PushSubscription subscription) {
        String eventTime = event.getEventDate().format(DateTimeFormatter.ofPattern("HH:mm"));
        String payload = String.format(
                """
                {
                    "title": "Event tomorrow",
                    "body": "%s is tomorrow at %s in %s.",
                    "eventId": %d
                }
                """,
                event.getTitle(),
                eventTime,
                event.getCity(),
                event.getId()
        );
        try {
            boolean sent = pushNotificationService.sendNotification(subscription, payload);
            if (!sent) {
                pushSubscriptionRepository.delete(subscription);
                System.out.println("Removed expired push subscription: " + subscription.getId());
            }
        } catch (Exception e) {
            System.err.println("Failed to send notification for event " + event.getId()
                            + " to subscription " + subscription.getId()
            );
            e.printStackTrace();
        }
    }
}