package com.saas.app_de_comida.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDTO {
    private String token;
    private Integer usuarioId;
    private String nombre;
    private String rol;
    private Integer restauranteId;
}
