package mk.ukim.finki.event_platform_backend.auth.service;

import mk.ukim.finki.event_platform_backend.auth.model.dto.LoginRequest;
import mk.ukim.finki.event_platform_backend.auth.model.dto.LoginResponse;
import mk.ukim.finki.event_platform_backend.auth.model.dto.RegisterRequest;

public interface AuthService {
    void register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
}
