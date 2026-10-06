package com.saas.app_de_comida.service.impl;

import com.saas.app_de_comida.dto.pedido.DetallePedidoRequestDTO;
import com.saas.app_de_comida.dto.pedido.EstadoPedidoRequestDTO;
import com.saas.app_de_comida.dto.pedido.PedidoRequestDTO;
import com.saas.app_de_comida.dto.pedido.PedidoResponseDTO;
import com.saas.app_de_comida.exception.BadRequestException;
import com.saas.app_de_comida.exception.ResourceNotFoundException;
import com.saas.app_de_comida.mapper.PedidoMapper;
import com.saas.app_de_comida.model.*;
import com.saas.app_de_comida.model.enums.EstadoPedido;
import com.saas.app_de_comida.model.enums.Role;
import com.saas.app_de_comida.repository.IPedidoRepository;
import com.saas.app_de_comida.repository.IProductoRepository;
import com.saas.app_de_comida.repository.IUsuarioRepository;
import com.saas.app_de_comida.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final IPedidoRepository pedidoRepository;
    private final IProductoRepository productoRepository;
    private final IUsuarioRepository usuarioRepository;
    private final PedidoMapper pedidoMapper;

    @Override
    @Transactional
    public PedidoResponseDTO createPedido(PedidoRequestDTO request, Usuario usuarioActual) {
        Pedido pedido = new Pedido();
        pedido.setFecha(LocalDateTime.now());
        pedido.setMetodoPago(request.getMetodoPago());
        pedido.setEstado(EstadoPedido.CONFIRMADO);
        
        Restaurante restaurante = null;

        if (usuarioActual.getRol() == Role.ADMIN) {
            pedido.setCajero(usuarioActual);
            restaurante = usuarioActual.getRestaurante();
            if (request.getNombreClienteLocal() != null && !request.getNombreClienteLocal().isBlank()) {
                pedido.setNombreClienteLocal(request.getNombreClienteLocal());
            } else if (request.getClienteId() != null) {
                Usuario cliente = usuarioRepository.findById(request.getClienteId())
                        .orElseThrow(() -> new ResourceNotFoundException("Cliente", request.getClienteId()));
                pedido.setCliente(cliente);
            }
        } else if (usuarioActual.getRol() == Role.CLIENTE) {
            pedido.setCliente(usuarioActual);
            // El cliente debe proporcionar algún mecanismo para saber el restaurante. 
            // Como temporalmente se asume un producto, tomaremos el restaurante del primer producto.
        } else {
            throw new BadRequestException("El rol actual no puede crear pedidos");
        }

        List<DetallePedido> detalles = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (DetallePedidoRequestDTO detDTO : request.getDetalles()) {
            Producto producto = productoRepository.findById(detDTO.getProductoCodigo())
                    .orElseThrow(() -> new ResourceNotFoundException("Producto con codigo " + detDTO.getProductoCodigo() + " no encontrado"));
            
            if (!producto.isDisponibilidad()) {
                throw new BadRequestException("El producto " + producto.getNombre() + " no está disponible.");
            }

            if (restaurante == null) {
                restaurante = producto.getRestaurante();
            } else if (!restaurante.getId().equals(producto.getRestaurante().getId())) {
                throw new BadRequestException("Todos los productos deben pertenecer al mismo restaurante.");
            }

            DetallePedido detalle = new DetallePedido();
            detalle.setProducto(producto);
            detalle.setCantidad(detDTO.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio());
            detalle.setPedido(pedido);

            detalles.add(detalle);

            BigDecimal subtotal = producto.getPrecio().multiply(BigDecimal.valueOf(detDTO.getCantidad()));
            total = total.add(subtotal);
        }

        pedido.setRestaurante(restaurante);
        pedido.setDetalles(detalles);
        pedido.setTotal(total);

        Pedido savedPedido = pedidoRepository.save(pedido);
        return pedidoMapper.toDTO(savedPedido);
    }

    @Override
    @Transactional(readOnly = true)
    public PedidoResponseDTO findByCodigo(String codigo) {
        Pedido pedido = pedidoRepository.findById(codigo)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido con codigo " + codigo + " no encontrado"));
        return pedidoMapper.toDTO(pedido);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> findByRestauranteId(Integer restauranteId) {
        return pedidoRepository.findByRestauranteId(restauranteId).stream()
                .map(pedidoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> findByClienteId(Integer clienteId) {
        return pedidoRepository.findByClienteId(clienteId).stream()
                .map(pedidoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PedidoResponseDTO changeEstado(String codigo, EstadoPedidoRequestDTO request, Usuario usuarioActual) {
        Pedido pedido = pedidoRepository.findById(codigo)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido con codigo " + codigo + " no encontrado"));

        // Validar transiciones
        EstadoPedido actual = pedido.getEstado();
        EstadoPedido nuevo = request.getEstado();

        if (nuevo == EstadoPedido.CANCELADO) {
            if (actual != EstadoPedido.CONFIRMADO) {
                throw new BadRequestException("Solo se pueden cancelar pedidos en estado CONFIRMADO");
            }
        } else {
            // Flujo normal: CONFIRMADO -> EN_PREPARACION -> LISTO -> ENTREGADO
            boolean isFlujoValido = false;
            if (actual == EstadoPedido.CONFIRMADO && nuevo == EstadoPedido.EN_PREPARACION) isFlujoValido = true;
            if (actual == EstadoPedido.EN_PREPARACION && nuevo == EstadoPedido.LISTO) isFlujoValido = true;
            if (actual == EstadoPedido.LISTO && nuevo == EstadoPedido.ENTREGADO) isFlujoValido = true;

            if (!isFlujoValido) {
                throw new BadRequestException("Transición de estado inválida de " + actual + " a " + nuevo);
            }
        }

        pedido.setEstado(nuevo);
        Pedido savedPedido = pedidoRepository.save(pedido);
        return pedidoMapper.toDTO(savedPedido);
    }
}
