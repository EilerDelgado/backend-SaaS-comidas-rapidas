package com.saas.app_de_comida.service;

import com.saas.app_de_comida.dto.usuario.UsuarioRequestDTO;
import com.saas.app_de_comida.dto.usuario.UsuarioResponseDTO;
import java.util.List;

public interface UsuarioService {
    UsuarioResponseDTO create(UsuarioRequestDTO request);
    List<UsuarioResponseDTO> findAll();
    UsuarioResponseDTO findById(Integer id);
    UsuarioResponseDTO update(Integer id, UsuarioRequestDTO request);
    void delete(Integer id);
}
