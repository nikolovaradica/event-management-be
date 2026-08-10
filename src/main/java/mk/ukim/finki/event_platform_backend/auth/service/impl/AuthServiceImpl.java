package mk.ukim.finki.event_platform_backend.auth.service.impl;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.event_platform_backend.auth.model.dto.LoginRequest;
import mk.ukim.finki.event_platform_backend.auth.model.dto.LoginResponse;
import mk.ukim.finki.event_platform_backend.auth.model.dto.RegisterRequest;
import mk.ukim.finki.event_platform_backend.auth.service.AuthService;
import mk.ukim.finki.event_platform_backend.common.exception.AlreadyExistsException;
import mk.ukim.finki.event_platform_backend.common.exception.BadRequestException;
import mk.ukim.finki.event_platform_backend.security.JwtService;
import mk.ukim.finki.event_platform_backend.user.model.Role;
import mk.ukim.finki.event_platform_backend.user.model.User;
import mk.ukim.finki.event_platform_backend.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public void register(RegisterRequest request) {
        if (!request.getPassword().equals(request.getRepeatPassword())) {
            throw new BadRequestException("Passwords do not match");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AlreadyExistsException("Email already exists");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AlreadyExistsException("Username already exists");
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmailOrUsername(request.getUsernameOrEmail(), request.getUsernameOrEmail())
                .orElseThrow(() -> new BadRequestException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid credentials");
        }

        String token = jwtService.generateToken(user);
        return new LoginResponse(token, user.getRole().name());
    }
}
