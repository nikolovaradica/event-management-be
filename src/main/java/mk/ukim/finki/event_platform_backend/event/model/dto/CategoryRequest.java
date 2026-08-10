package mk.ukim.finki.event_platform_backend.event.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequest {
    @NotBlank(message = "Category name is required")
    @Size(max = 100)
    private String name;
    @Size(max = 1000)
    private String description;
}
