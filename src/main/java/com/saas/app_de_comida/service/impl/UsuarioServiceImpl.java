package com.saas.app_de_comida.service.impl;

import com.saas.app_de_comida.dto.usuario.UsuarioRequestDTO;
import com.saas.app_de_comida.dto.usuario.UsuarioResponseDTO;
import com.saas.app_de_comida.exception.DuplicateResourceException;
import com.saas.app_de_comida.exception.ResourceNotFoundException;
import com.saas.app_de_comida.mapper.UsuarioMapper;
import com.saas.app_de_comida.model.Restaurante;
import com.saas.app_de_comida.model.Usuario;
import com.saas.app_de_comida.repository.IRestauranteRepository;
import com.saas.app_de_comida.repository.IUsuarioRepository;
import com.saas.app_de_comida.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final IUsuarioRepository usuarioRepository;
    private final IRestauranteRepository restauranteRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UsuarioResponseDTO create(UsuarioRequestDTO request) {
        if (usuarioRepository.existsByCorreo(request.getCorreo())) {
            throw new DuplicateResourceException("Ya existe un usuario con el correo: " + request.getCorreo());
        }

        Usuario usuario = usuarioMapper.toEntity(request);
        
        // Hashear contraseña antes de guardar
        usuario.setContrasena(passwordEncoder.encode(request.getContrasena()));
        
        if (request.getRestauranteId() != null) {
            Restaurante restaurante = restauranteRepository.findById(request.getRestauranteId())
                    .orElseThrow(() -> new ResourceNotFoundException("Restaurante", request.getRestauranteId()));
            usuario.setRestaurante(restaurante);
        }

        Usuario savedUsuario = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(savedUsuario);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO registerCliente(com.saas.app_de_comida.dto.auth.AuthRegisterDTO request) {
        if (usuarioRepository.existsByCorreo(request.getCorreo())) {
            throw new DuplicateResourceException("Ya existe un usuario con el correo: " + request.getCorreo());
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setCorreo(request.getCorreo());
        usuario.setContrasena(passwordEncoder.encode(request.getContrasena()));
        usuario.setRol(com.saas.app_de_comida.model.enums.Role.CLIENTE);
        // Cliente no tiene restaurante asignado
        
        Usuario savedUsuario = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(savedUsuario);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO createAdmin(UsuarioRequestDTO request) {
        if (usuarioRepository.existsByCorreo(request.getCorreo())) {
            throw new DuplicateResourceException("Ya existe un usuario con el correo: " + request.getCorreo());
        }

        Usuario usuario = usuarioMapper.toEntity(request);
        usuario.setContrasena(passwordEncoder.encode(request.getContrasena()));
        usuario.setRol(com.saas.app_de_comida.model.enums.Role.ADMIN); // Ignorar el rol que envíen, forzar ADMIN

        if (request.getRestauranteId() != null) {
            Restaurante restaurante = restauranteRepository.findById(request.getRestauranteId())
                    .orElseThrow(() -> new ResourceNotFoundException("Restaurante", request.getRestauranteId()));
            usuario.setRestaurante(restaurante);
        }

        Usuario savedUsuario = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(savedUsuario);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO createCocinero(UsuarioRequestDTO request, Integer adminRestauranteId) {
        if (usuarioRepository.existsByCorreo(request.getCorreo())) {
            throw new DuplicateResourceException("Ya existe un usuario con el correo: " + request.getCorreo());
        }

        Usuario usuario = usuarioMapper.toEntity(request);
        usuario.setContrasena(passwordEncoder.encode(request.getContrasena()));
        usuario.setRol(com.saas.app_de_comida.model.enums.Role.COCINA); // Forzar rol COCINA

        // Asignar el restaurante del admin directamente
        if (adminRestauranteId != null) {
            Restaurante restaurante = restauranteRepository.findById(adminRestauranteId)
                    .orElseThrow(() -> new ResourceNotFoundException("Restaurante", adminRestauranteId));
            usuario.setRestaurante(restaurante);
        } else {
            throw new IllegalArgumentException("El administrador que crea al cocinero debe pertenecer a un restaurante.");
        }

        Usuario savedUsuario = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(savedUsuario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> findAll() {
        return usuarioRepository.findAll().stream()
                .map(usuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDTO findById(Integer id) {
        Usuario usuario = getUsuarioById(id);
        return usuarioMapper.toDTO(usuario);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO update(Integer id, UsuarioRequestDTO request) {
        Usuario usuario = getUsuarioById(id);

        if (!usuario.getCorreo().equals(request.getCorreo()) && 
            usuarioRepository.existsByCorreo(request.getCorreo())) {
            throw new DuplicateResourceException("Ya existe un usuario con el correo: " + request.getCorreo());
        }

        usuario.setNombre(request.getNombre());
        usuario.setCorreo(request.getCorreo());
        usuario.setRol(request.getRol());
        
        if (request.getRestauranteId() != null) {
            Restaurante restaurante = restauranteRepository.findById(request.getRestauranteId())
                    .orElseThrow(() -> new ResourceNotFoundException("Restaurante", request.getRestauranteId()));
            usuario.setRestaurante(restaurante);
        } else {
            usuario.setRestaurante(null);
        }
        
        if (request.getContrasena() != null && !request.getContrasena().trim().isEmpty()) {
            usuario.setContrasena(passwordEncoder.encode(request.getContrasena()));
        }

        Usuario updatedUsuario = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(updatedUsuario);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Usuario usuario = getUsuarioById(id);
        usuarioRepository.delete(usuario);
    }

    private Usuario getUsuarioById(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", id));
    }
}
