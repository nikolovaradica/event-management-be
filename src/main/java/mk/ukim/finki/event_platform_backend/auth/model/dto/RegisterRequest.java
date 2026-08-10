package mk.ukim.finki.event_platform_backend.auth.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    @NotBlank(message = "First name is required")
    @Size(max = 100)
    private String firstName;
    @NotBlank(message = "Last name is required")
    @Size(max = 100)
    private String lastName;
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50)
    private String username;
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 100)
    private String password;
    @NotBlank(message = "Repeat password is required")
    private String repeatPassword;
}
