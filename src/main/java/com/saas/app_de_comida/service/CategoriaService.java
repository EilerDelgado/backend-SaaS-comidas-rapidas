package com.saas.app_de_comida.service;

import com.saas.app_de_comida.dto.categoria.CategoriaRequestDTO;
import com.saas.app_de_comida.dto.categoria.CategoriaResponseDTO;

import java.util.List;

public interface CategoriaService {
    CategoriaResponseDTO create(CategoriaRequestDTO request);
    List<CategoriaResponseDTO> findAll();
    CategoriaResponseDTO findById(Integer id);
    CategoriaResponseDTO update(Integer id, CategoriaRequestDTO request);
    void delete(Integer id);
}
