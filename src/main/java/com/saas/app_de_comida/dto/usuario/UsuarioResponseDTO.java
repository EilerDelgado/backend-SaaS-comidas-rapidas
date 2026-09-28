package com.saas.app_de_comida.dto.usuario;

import com.saas.app_de_comida.model.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioResponseDTO {
    private Integer id;
    private String nombre;
    private String correo;
    private Role rol;
}
