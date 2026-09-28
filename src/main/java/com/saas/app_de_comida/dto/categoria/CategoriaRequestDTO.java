package com.saas.app_de_comida.dto.categoria;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaRequestDTO {
    @NotBlank(message = "El nombre de la categoría es obligatorio")
    private String nombre;

    @NotNull(message = "El ID del restaurante es obligatorio")
    private Integer restauranteId;
}
