package com.sistemadenuncias.sistemadenuncias.controllers;

import com.sistemadenuncias.sistemadenuncias.DTO.AuthRequestDTO;
import com.sistemadenuncias.sistemadenuncias.DTO.AuthResponseDTO;
import com.sistemadenuncias.sistemadenuncias.services.CustomUserDetailsService;
import com.sistemadenuncias.sistemadenuncias.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    public AuthController(AuthenticationManager authManager, JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO dto) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha())
        );

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String accessToken = jwtUtil.gerarAccessToken(userDetails);
        String refreshToken = jwtUtil.gerarRefreshToken(userDetails);

        return ResponseEntity.ok(new AuthResponseDTO(accessToken, refreshToken));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDTO> refresh(@RequestBody Map<String, String> body) {
        String refreshToken = body.get("refreshToken");
        String username = jwtUtil.extrairUsername(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (!jwtUtil.validarToken(refreshToken, userDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String newAccessToken = jwtUtil.gerarAccessToken(userDetails);
        String newRefreshToken = jwtUtil.gerarRefreshToken(userDetails);

        return ResponseEntity.ok(new AuthResponseDTO(newAccessToken, newRefreshToken));
    }
}
