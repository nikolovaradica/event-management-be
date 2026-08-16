package mk.ukim.finki.event_platform_backend.notification.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class VapidProperties {
    @Value("${vapid.public-key}")
    private String publicKey;
    @Value("${vapid.private-key}")
    private String privateKey;
    @Value("${vapid.subject}")
    private String subject;
}