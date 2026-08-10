package mk.ukim.finki.event_platform_backend.event.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class EventResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime eventDate;
    private String city;
    private String venue;
    private String imageUrl;
    private LocalDateTime createdAt;
    private CategoryResponse category;
    private OrganizerResponse organizer;
}
