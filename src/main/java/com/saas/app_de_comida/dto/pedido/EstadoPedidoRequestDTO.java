package com.saas.app_de_comida.dto.pedido;

import com.saas.app_de_comida.model.enums.EstadoPedido;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EstadoPedidoRequestDTO {

    @NotNull(message = "El estado es obligatorio")
    private EstadoPedido estado;
}
