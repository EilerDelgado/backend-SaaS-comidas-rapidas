package com.saas.app_de_comida.dto.pedido;

import com.saas.app_de_comida.model.enums.EstadoPedido;
import com.saas.app_de_comida.model.enums.MetodoPago;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PedidoResponseDTO {
    private String codigo;
    private LocalDateTime fecha;
    private BigDecimal total;
    private MetodoPago metodoPago;
    private EstadoPedido estado;
    private Integer clienteId;
    private String clienteNombre;
    private Integer cajeroId;
    private String nombreClienteLocal;
    private Integer restauranteId;
    private List<DetallePedidoResponseDTO> detalles;
}
