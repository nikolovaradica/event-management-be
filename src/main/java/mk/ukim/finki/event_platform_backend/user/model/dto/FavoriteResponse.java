package mk.ukim.finki.event_platform_backend.user.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class FavoriteResponse {
    private Long id;
    private Long eventId;
    private String eventTitle;
    private LocalDateTime eventDate;
    private String city;
    private String venue;
    private String imageUrl;
    private String categoryName;
    private String organizerName;
}
