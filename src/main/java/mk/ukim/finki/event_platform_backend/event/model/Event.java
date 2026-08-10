package mk.ukim.finki.event_platform_backend.event.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false, length = 5000)
    private String description;
    @Column(nullable = false)
    private LocalDateTime eventDate;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String venue;
    private String imageUrl;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @ManyToOne(optional = false)
    private Category category;
    @ManyToOne(optional = false)
    private Organizer organizer;
}
