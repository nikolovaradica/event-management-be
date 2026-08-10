package mk.ukim.finki.event_platform_backend.event.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventRequest {
    @NotBlank(message = "Title is required")
    @Size(max = 255)
    private String title;
    @NotBlank(message = "Description is required")
    @Size(max = 5000)
    private String description;
    @NotNull(message = "Event date is required")
    private LocalDateTime eventDate;
    @NotBlank(message = "City is required")
    @Size(max = 100)
    private String city;
    @NotBlank(message = "Venue is required")
    @Size(max = 255)
    private String venue;
    @Size(max = 1000)
    private String imageUrl;
    @NotNull(message = "Category is required")
    private Long categoryId;
    @NotNull(message = "Organizer is required")
    private Long organizerId;
}