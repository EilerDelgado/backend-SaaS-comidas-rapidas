package com.saas.app_de_comida.controller;

import com.saas.app_de_comida.dto.producto.ProductoRequestDTO;
import com.saas.app_de_comida.dto.producto.ProductoResponseDTO;
import com.saas.app_de_comida.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @PostMapping
    public ResponseEntity<ProductoResponseDTO> create(@Valid @RequestBody ProductoRequestDTO request) {
        return new ResponseEntity<>(productoService.create(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> findAll() {
        return ResponseEntity.ok(productoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> findById(@PathVariable String id) {
        return ResponseEntity.ok(productoService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> update(
            @PathVariable String id, 
            @Valid @RequestBody ProductoRequestDTO request) {
        return ResponseEntity.ok(productoService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/disponibilidad")
    public ResponseEntity<ProductoResponseDTO> updateDisponibilidad(
            @PathVariable String id,
            @RequestParam boolean disponible) {
        return ResponseEntity.ok(productoService.updateDisponibilidad(id, disponible));
    }
}
