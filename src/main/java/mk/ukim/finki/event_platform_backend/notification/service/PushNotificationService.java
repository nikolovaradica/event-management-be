package mk.ukim.finki.event_platform_backend.notification.service;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.event_platform_backend.notification.config.VapidProperties;
import mk.ukim.finki.event_platform_backend.notification.model.PushSubscription;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.jwt.jose4j.JOSE4jJwtFactory;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;
import java.security.Security;

@Service
@RequiredArgsConstructor
public class PushNotificationService {
    private final VapidProperties vapidProperties;

    public boolean sendNotification(PushSubscription pushSubscription, String payload) throws Exception {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }

        PushService pushService = PushService.builder()
                .withVapidPublicKey(vapidProperties.getPublicKey())
                .withVapidPrivateKey(vapidProperties.getPrivateKey())
                .withVapidSubject(vapidProperties.getSubject())
                .withJwtFactory(new JOSE4jJwtFactory())
                .build();

        Notification notification = Notification.builder()
                .endpoint(pushSubscription.getEndpoint())
                .userPublicKey(pushSubscription.getP256dh())
                .userAuth(pushSubscription.getAuth())
                .payload(payload)
                .ttl(60 * 60)
                .build();

        HttpResponse<Void> response = pushService.send(notification);
        int statusCode = response.statusCode();
        if (statusCode == 404 || statusCode == 410) {
            return false;
        }
        return statusCode >= 200 && statusCode < 300;
    }
}