package mk.ukim.finki.event_platform_backend.notification.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PushSubscriptionRequest {
    private String endpoint;
    private String p256dh;
    private String auth;
}