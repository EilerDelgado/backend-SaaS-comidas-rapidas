package com.saas.app_de_comida.service.impl;

import com.saas.app_de_comida.dto.producto.ProductoRequestDTO;
import com.saas.app_de_comida.dto.producto.ProductoResponseDTO;
import com.saas.app_de_comida.exception.DuplicateResourceException;
import com.saas.app_de_comida.exception.ResourceNotFoundException;
import com.saas.app_de_comida.mapper.ProductoMapper;
import com.saas.app_de_comida.model.Categoria;
import com.saas.app_de_comida.model.Producto;
import com.saas.app_de_comida.repository.ICategoriaRepository;
import com.saas.app_de_comida.repository.IProductoRepository;
import com.saas.app_de_comida.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final IProductoRepository productoRepository;
    private final ICategoriaRepository categoriaRepository;
    private final ProductoMapper productoMapper;

    @Override
    @Transactional
    public ProductoResponseDTO create(ProductoRequestDTO request) {
        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría", request.getCategoriaId()));

        Producto producto = productoMapper.toEntity(request);
        producto.setDisponibilidad(true); // Al crear, por defecto está disponible
        producto.setCategoriaProducto(categoria);

        Producto savedProducto = productoRepository.save(producto);
        return productoMapper.toDTO(savedProducto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> findAll() {
        return productoRepository.findAll().stream()
                .map(productoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoResponseDTO findById(String codigo) {
        Producto producto = getProductoById(codigo);
        return productoMapper.toDTO(producto);
    }

    @Override
    @Transactional
    public ProductoResponseDTO update(String codigo, ProductoRequestDTO request) {
        Producto producto = getProductoById(codigo);

        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría", request.getCategoriaId()));

        // El código no se modifica porque es generado y único
        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecio(request.getPrecio());
        producto.setCategoriaProducto(categoria);

        Producto updatedProducto = productoRepository.save(producto);
        return productoMapper.toDTO(updatedProducto);
    }

    @Override
    @Transactional
    public void delete(String codigo) {
        Producto producto = getProductoById(codigo);
        productoRepository.delete(producto);
    }

    @Override
    @Transactional
    public ProductoResponseDTO updateDisponibilidad(String codigo, boolean disponibilidad) {
        Producto producto = getProductoById(codigo);
        producto.setDisponibilidad(disponibilidad);
        Producto updatedProducto = productoRepository.save(producto);
        return productoMapper.toDTO(updatedProducto);
    }

    private Producto getProductoById(String codigo) {
        return productoRepository.findById(codigo)
                .orElseThrow(() -> new ResourceNotFoundException("Producto con código " + codigo + " no encontrado"));
    }
}
