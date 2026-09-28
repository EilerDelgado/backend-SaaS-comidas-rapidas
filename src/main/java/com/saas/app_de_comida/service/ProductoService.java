package com.saas.app_de_comida.service;

import com.saas.app_de_comida.dto.producto.ProductoRequestDTO;
import com.saas.app_de_comida.dto.producto.ProductoResponseDTO;

import java.util.List;

public interface ProductoService {
    ProductoResponseDTO create(ProductoRequestDTO request);
    List<ProductoResponseDTO> findAll();
    ProductoResponseDTO findById(String codigo);
    ProductoResponseDTO update(String codigo, ProductoRequestDTO request);
    void delete(String codigo);
    ProductoResponseDTO updateDisponibilidad(String codigo, boolean disponibilidad);
}
