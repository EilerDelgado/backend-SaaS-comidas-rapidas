package com.saas.app_de_comida.mapper;

import com.saas.app_de_comida.dto.pedido.DetallePedidoResponseDTO;
import com.saas.app_de_comida.dto.pedido.PedidoResponseDTO;
import com.saas.app_de_comida.model.DetallePedido;
import com.saas.app_de_comida.model.Pedido;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PedidoMapper {

    public PedidoResponseDTO toDTO(Pedido entity) {
        PedidoResponseDTO dto = new PedidoResponseDTO();
        dto.setCodigo(entity.getCodigo());
        dto.setFecha(entity.getFecha());
        dto.setTotal(entity.getTotal());
        dto.setMetodoPago(entity.getMetodoPago());
        dto.setEstado(entity.getEstado());

        if (entity.getCliente() != null) {
            dto.setClienteId(entity.getCliente().getId());
            dto.setClienteNombre(entity.getCliente().getNombre());
        }

        if (entity.getCajero() != null) {
            dto.setCajeroId(entity.getCajero().getId());
        }

        dto.setNombreClienteLocal(entity.getNombreClienteLocal());

        if (entity.getRestaurante() != null) {
            dto.setRestauranteId(entity.getRestaurante().getId());
        }

        if (entity.getDetalles() != null) {
            dto.setDetalles(entity.getDetalles().stream()
                    .map(this::toDetalleDTO)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    private DetallePedidoResponseDTO toDetalleDTO(DetallePedido entity) {
        DetallePedidoResponseDTO dto = new DetallePedidoResponseDTO();
        dto.setId(entity.getId());
        if (entity.getProducto() != null) {
            dto.setProductoCodigo(entity.getProducto().getCodigo());
            dto.setProductoNombre(entity.getProducto().getNombre());
        }
        dto.setCantidad(entity.getCantidad());
        dto.setPrecioUnitario(entity.getPrecioUnitario());
        dto.setSubtotal(entity.getPrecioUnitario().multiply(java.math.BigDecimal.valueOf(entity.getCantidad())));
        return dto;
    }
}
