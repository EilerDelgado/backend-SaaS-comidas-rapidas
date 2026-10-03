package com.saas.app_de_comida.service;

import com.saas.app_de_comida.dto.usuario.UsuarioRequestDTO;
import com.saas.app_de_comida.dto.usuario.UsuarioResponseDTO;
import java.util.List;

public interface UsuarioService {
    UsuarioResponseDTO create(UsuarioRequestDTO request);
    UsuarioResponseDTO registerCliente(com.saas.app_de_comida.dto.auth.AuthRegisterDTO request);
    UsuarioResponseDTO createAdmin(UsuarioRequestDTO request);
    UsuarioResponseDTO createCocinero(UsuarioRequestDTO request, Integer adminRestauranteId);
    List<UsuarioResponseDTO> findAll();
    UsuarioResponseDTO findById(Integer id);
    UsuarioResponseDTO update(Integer id, UsuarioRequestDTO request);
    void delete(Integer id);
}
