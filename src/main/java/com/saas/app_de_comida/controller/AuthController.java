package com.saas.app_de_comida.controller;

import com.saas.app_de_comida.dto.auth.AuthRequestDTO;
import com.saas.app_de_comida.dto.auth.AuthResponseDTO;
import com.saas.app_de_comida.security.JwtService;
import com.saas.app_de_comida.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO request) {
        
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getCorreo(),
                        request.getContrasena()
                )
        );

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        
        String jwtToken = jwtService.generateToken(userDetails);

        AuthResponseDTO response = new AuthResponseDTO(
                jwtToken,
                userDetails.getUsuario().getId(),
                userDetails.getUsuario().getNombre(),
                userDetails.getUsuario().getRol().name(),
                userDetails.getUsuario().getRestaurante() != null ? userDetails.getUsuario().getRestaurante().getId() : null
        );

        return ResponseEntity.ok(response);
    }
}
