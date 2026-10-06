package com.saas.app_de_comida.controller;

import com.saas.app_de_comida.dto.pedido.EstadoPedidoRequestDTO;
import com.saas.app_de_comida.dto.pedido.PedidoRequestDTO;
import com.saas.app_de_comida.dto.pedido.PedidoResponseDTO;
import com.saas.app_de_comida.model.enums.Role;
import com.saas.app_de_comida.security.UserDetailsImpl;
import com.saas.app_de_comida.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> create(
            @Valid @RequestBody PedidoRequestDTO request,
            Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return new ResponseEntity<>(pedidoService.createPedido(request, userDetails.getUsuario()), HttpStatus.CREATED);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<PedidoResponseDTO> findByCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(pedidoService.findByCodigo(codigo));
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> findAll(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if (userDetails.getUsuario().getRol() == Role.ADMIN || userDetails.getUsuario().getRol() == Role.COCINA) {
            Integer restauranteId = userDetails.getUsuario().getRestaurante().getId();
            return ResponseEntity.ok(pedidoService.findByRestauranteId(restauranteId));
        } else if (userDetails.getUsuario().getRol() == Role.CLIENTE) {
            return ResponseEntity.ok(pedidoService.findByClienteId(userDetails.getUsuario().getId()));
        }
        return ResponseEntity.badRequest().build();
    }

    @PatchMapping("/{codigo}/estado")
    public ResponseEntity<PedidoResponseDTO> changeEstado(
            @PathVariable String codigo,
            @Valid @RequestBody EstadoPedidoRequestDTO request,
            Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return ResponseEntity.ok(pedidoService.changeEstado(codigo, request, userDetails.getUsuario()));
    }
}
