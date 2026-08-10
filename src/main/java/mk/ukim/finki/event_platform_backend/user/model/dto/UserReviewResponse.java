package mk.ukim.finki.event_platform_backend.user.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class UserReviewResponse {
    private Long id;
    private Long eventId;
    private String eventTitle;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
}
