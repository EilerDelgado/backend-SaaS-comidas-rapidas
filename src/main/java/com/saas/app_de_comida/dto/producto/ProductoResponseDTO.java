package com.saas.app_de_comida.dto.producto;

import com.saas.app_de_comida.dto.categoria.CategoriaResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductoResponseDTO {
    private String codigo;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private boolean disponibilidad;
    private CategoriaResponseDTO categoria;
}
