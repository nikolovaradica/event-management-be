package mk.ukim.finki.event_platform_backend.event.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CategoryResponse {
    private Long id;
    private String name;
    private String description;
}