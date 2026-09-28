package com.saas.app_de_comida.service.impl;

import com.saas.app_de_comida.dto.categoria.CategoriaRequestDTO;
import com.saas.app_de_comida.dto.categoria.CategoriaResponseDTO;
import com.saas.app_de_comida.exception.DuplicateResourceException;
import com.saas.app_de_comida.exception.ResourceNotFoundException;
import com.saas.app_de_comida.mapper.CategoriaMapper;
import com.saas.app_de_comida.model.Categoria;
import com.saas.app_de_comida.model.Restaurante;
import com.saas.app_de_comida.repository.ICategoriaRepository;
import com.saas.app_de_comida.repository.IRestauranteRepository;
import com.saas.app_de_comida.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final ICategoriaRepository categoriaRepository;
    private final IRestauranteRepository restauranteRepository;
    private final CategoriaMapper categoriaMapper;

    @Override
    @Transactional
    public CategoriaResponseDTO create(CategoriaRequestDTO request) {
        Restaurante restaurante = restauranteRepository.findById(request.getRestauranteId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante", request.getRestauranteId()));

        if (categoriaRepository.existsByNombreAndRestauranteId(request.getNombre(), restaurante.getId())) {
            throw new DuplicateResourceException("Ya existe una categoría con el nombre en este restaurante.");
        }
        Categoria categoria = categoriaMapper.toEntity(request);
        categoria.setRestaurante(restaurante);

        Categoria savedCategoria = categoriaRepository.save(categoria);
        return categoriaMapper.toDTO(savedCategoria);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> findAll() {
        return categoriaRepository.findAll().stream()
                .map(categoriaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaResponseDTO findById(Integer id) {
        Categoria categoria = getCategoriaById(id);
        return categoriaMapper.toDTO(categoria);
    }

    @Override
    @Transactional
    public CategoriaResponseDTO update(Integer id, CategoriaRequestDTO request) {
        Categoria categoria = getCategoriaById(id);
        Restaurante restaurante = restauranteRepository.findById(request.getRestauranteId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante", request.getRestauranteId()));

        if (!categoria.getNombre().equals(request.getNombre()) && 
            categoriaRepository.existsByNombreAndRestauranteId(request.getNombre(), restaurante.getId())) {
            throw new DuplicateResourceException("Ya existe una categoría con el nombre en este restaurante.");
        }

        categoria.setNombre(request.getNombre());
        categoria.setRestaurante(restaurante);

        Categoria updatedCategoria = categoriaRepository.save(categoria);
        return categoriaMapper.toDTO(updatedCategoria);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Categoria categoria = getCategoriaById(id);
        categoriaRepository.delete(categoria);
    }

    private Categoria getCategoriaById(Integer id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría", id));
    }
}
