package com.saas.app_de_comida.mapper;

import com.saas.app_de_comida.dto.categoria.CategoriaRequestDTO;
import com.saas.app_de_comida.dto.categoria.CategoriaResponseDTO;
import com.saas.app_de_comida.model.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {

    public Categoria toEntity(CategoriaRequestDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNombre(dto.getNombre());
        return categoria;
    }

    public CategoriaResponseDTO toDTO(Categoria entity) {
        CategoriaResponseDTO dto = new CategoriaResponseDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        return dto;
    }
}
