package mk.ukim.finki.event_platform_backend.event.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganizerRequest {
    @NotBlank(message = "Organizer name is required")
    @Size(max = 200)
    private String name;
    @Size(max = 2000)
    private String description;
    @Size(max = 500)
    private String website;
}
