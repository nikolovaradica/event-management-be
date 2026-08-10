package mk.ukim.finki.event_platform_backend.security;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.event_platform_backend.common.exception.NotFoundException;
import mk.ukim.finki.event_platform_backend.user.model.User;
import mk.ukim.finki.event_platform_backend.user.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityUtils {
    private final UserRepository userRepository;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Authenticated user not found"));
    }
}
