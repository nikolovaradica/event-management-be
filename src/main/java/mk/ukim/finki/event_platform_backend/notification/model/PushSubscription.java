package mk.ukim.finki.event_platform_backend.notification.model;

import jakarta.persistence.*;
import lombok.*;
import mk.ukim.finki.event_platform_backend.user.model.User;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PushSubscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 1000)
    private String endpoint;
    @Column(nullable = false, length = 500)
    private String p256dh;
    @Column(nullable = false, length = 500)
    private String auth;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}