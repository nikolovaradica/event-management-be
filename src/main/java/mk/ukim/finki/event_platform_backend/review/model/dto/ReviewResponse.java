package mk.ukim.finki.event_platform_backend.review.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class ReviewResponse {
    private Long id;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
    private Long userId;
    private String username;
}
