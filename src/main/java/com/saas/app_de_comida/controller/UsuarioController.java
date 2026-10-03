package com.saas.app_de_comida.controller;

import com.saas.app_de_comida.dto.usuario.UsuarioRequestDTO;
import com.saas.app_de_comida.dto.usuario.UsuarioResponseDTO;
import com.saas.app_de_comida.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> create(@Valid @RequestBody UsuarioRequestDTO request) {
        return new ResponseEntity<>(usuarioService.create(request), HttpStatus.CREATED);
    }

    @PostMapping("/admin")
    public ResponseEntity<UsuarioResponseDTO> createAdmin(@Valid @RequestBody UsuarioRequestDTO request) {
        return new ResponseEntity<>(usuarioService.createAdmin(request), HttpStatus.CREATED);
    }

    @PostMapping("/cocinero")
    public ResponseEntity<UsuarioResponseDTO> createCocinero(
            @Valid @RequestBody UsuarioRequestDTO request, 
            org.springframework.security.core.Authentication authentication) {
        
        com.saas.app_de_comida.security.UserDetailsImpl userDetails = 
                (com.saas.app_de_comida.security.UserDetailsImpl) authentication.getPrincipal();
        
        Integer adminRestauranteId = null;
        if (userDetails.getUsuario().getRestaurante() != null) {
            adminRestauranteId = userDetails.getUsuario().getRestaurante().getId();
        }

        return new ResponseEntity<>(usuarioService.createCocinero(request, adminRestauranteId), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> findAll() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> update(
            @PathVariable Integer id, 
            @Valid @RequestBody UsuarioRequestDTO request) {
        return ResponseEntity.ok(usuarioService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
