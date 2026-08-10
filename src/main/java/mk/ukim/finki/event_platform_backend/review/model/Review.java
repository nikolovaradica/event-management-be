package mk.ukim.finki.event_platform_backend.review.model;

import jakarta.persistence.*;
import lombok.*;
import mk.ukim.finki.event_platform_backend.event.model.Event;
import mk.ukim.finki.event_platform_backend.user.model.User;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Integer rating;
    @Column(length = 2000)
    private String comment;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @ManyToOne(optional = false)
    private User user;
    @ManyToOne(optional = false)
    private Event event;
}
