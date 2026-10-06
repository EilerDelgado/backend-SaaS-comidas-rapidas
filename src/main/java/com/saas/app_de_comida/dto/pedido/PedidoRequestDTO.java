package com.saas.app_de_comida.dto.pedido;

import com.saas.app_de_comida.model.enums.MetodoPago;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class PedidoRequestDTO {

    @NotNull(message = "El método de pago es obligatorio")
    private MetodoPago metodoPago;

    private Integer clienteId; // Opcional, para el flujo online
    
    private String nombreClienteLocal; // Opcional, para el flujo POS

    @NotEmpty(message = "El pedido debe tener al menos un detalle")
    @Valid
    private List<DetallePedidoRequestDTO> detalles;
}
