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
    private final com.saas.app_de_comida.service.UsuarioService usuarioService;

    @PostMapping("/registro-cliente")
    public ResponseEntity<com.saas.app_de_comida.dto.usuario.UsuarioResponseDTO> registroCliente(@Valid @RequestBody com.saas.app_de_comida.dto.auth.AuthRegisterDTO request) {
        return new ResponseEntity<>(usuarioService.registerCliente(request), org.springframework.http.HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO request) {
        
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getCorreo(),
                        request.getContrasena()
                )
        );

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        
        java.util.Map<String, Object> extraClaims = new java.util.HashMap<>();
        extraClaims.put("id_usuario", userDetails.getUsuario().getId());
        extraClaims.put("rol", userDetails.getUsuario().getRol().name());
        if (userDetails.getUsuario().getRestaurante() != null) {
            extraClaims.put("restaurante_id", userDetails.getUsuario().getRestaurante().getId());
        }

        String jwtToken = jwtService.generateToken(extraClaims, userDetails);
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
