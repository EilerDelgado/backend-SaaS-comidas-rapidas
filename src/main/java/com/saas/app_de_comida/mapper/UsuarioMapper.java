package com.saas.app_de_comida.mapper;

import com.saas.app_de_comida.dto.usuario.UsuarioRequestDTO;
import com.saas.app_de_comida.dto.usuario.UsuarioResponseDTO;
import com.saas.app_de_comida.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toEntity(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setCorreo(dto.getCorreo());
        usuario.setContrasena(dto.getContrasena());
        usuario.setRol(dto.getRol());
        return usuario;
    }

    public UsuarioResponseDTO toDTO(Usuario entity) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setCorreo(entity.getCorreo());
        dto.setRol(entity.getRol());
        return dto;
    }
}
