package com.backend.project.psb.services;

import com.backend.project.psb.config.JwtService;
import com.backend.project.psb.dto.AuthenticationRequest;
import com.backend.project.psb.dto.AuthenticationResponse;
import com.backend.project.psb.dto.RegisterRequest;
import com.backend.project.psb.model.Role;
import com.backend.project.psb.model.User;
import com.backend.project.psb.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {


    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final AuthenticationManager authenticationManager;

    public void register(RegisterRequest request) {
        log.info("Registering the user...");
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        userRepo.save(user);

    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        log.info("Authenticating the user...");
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Role role = user.getRole();
        Map<String, Object> extraClaims = new HashMap<String, Object>();
        extraClaims.put("role", role);
        String jwtToken = jwtService.generateToken(extraClaims, user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }
}
