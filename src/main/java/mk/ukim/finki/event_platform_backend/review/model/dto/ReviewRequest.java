package mk.ukim.finki.event_platform_backend.review.model.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequest {
    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer rating;
    @NotBlank(message = "Comment is required")
    @Size(max = 1000)
    private String comment;
}