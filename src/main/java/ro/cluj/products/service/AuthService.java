package ro.cluj.products.service;

import ro.cluj.products.dto.JwtAuthResponse;
import ro.cluj.products.dto.LoginDto;
import ro.cluj.products.dto.RegisterDto;

public interface AuthService {
    String register(RegisterDto registerDto);
    JwtAuthResponse login(LoginDto loginDto);
}