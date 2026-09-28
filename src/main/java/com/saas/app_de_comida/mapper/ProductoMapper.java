package com.saas.app_de_comida.mapper;

import com.saas.app_de_comida.dto.producto.ProductoRequestDTO;
import com.saas.app_de_comida.dto.producto.ProductoResponseDTO;
import com.saas.app_de_comida.model.Producto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductoMapper {

    private final CategoriaMapper categoriaMapper;

    public Producto toEntity(ProductoRequestDTO dto) {
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        // disponibilidad se asume true por defecto en el servicio, o se puede setear acá.
        return producto;
    }

    public ProductoResponseDTO toDTO(Producto entity) {
        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setCodigo(entity.getCodigo());
        dto.setNombre(entity.getNombre());
        dto.setDescripcion(entity.getDescripcion());
        dto.setPrecio(entity.getPrecio());
        dto.setDisponibilidad(entity.isDisponibilidad());
        
        if (entity.getCategoriaProducto() != null) {
            dto.setCategoria(categoriaMapper.toDTO(entity.getCategoriaProducto()));
        }
        
        return dto;
    }
}
