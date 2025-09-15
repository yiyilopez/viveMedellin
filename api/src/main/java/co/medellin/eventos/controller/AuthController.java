package co.medellin.eventos.controller;

import co.medellin.eventos.dto.*;
import co.medellin.eventos.model.User;
import co.medellin.eventos.repository.UserRepository;
import co.medellin.eventos.security.JwtService;
import co.medellin.eventos.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        User user = User.builder()
                .name(request.getName())
                .username(request.getUsername())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role("USER")
                .isActive(true)
                .build();
        userService.save(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        User user = (User) authentication.getPrincipal();
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateToken(user); // For demo, use same method; in prod, use different claims/expiration
        UserSummary userSummary = new UserSummary(user.getId(), user.getName(), user.getUsername(), user.getEmail(), user.getRole());
        return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken, userSummary));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@Valid @RequestBody RefreshRequest request) {
    String username = jwtService.extractUsername(request.getRefreshToken());
    User user = userRepository.findByUsername(username).orElseThrow();
    String accessToken = jwtService.generateToken(user);
    UserSummary userSummary = new UserSummary(user.getId(), user.getName(), user.getUsername(), user.getEmail(), user.getRole());
    return ResponseEntity.ok(new AuthResponse(accessToken, request.getRefreshToken(), userSummary));
    }
}
