package mk.ukim.finki.event_platform_backend.user.model;

import jakarta.persistence.*;
import lombok.*;
import mk.ukim.finki.event_platform_backend.event.model.Event;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    private User user;
    @ManyToOne(optional = false)
    private Event event;
}