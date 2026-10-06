package com.saas.app_de_comida.dto.pedido;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DetallePedidoResponseDTO {
    private Integer id;
    private String productoCodigo;
    private String productoNombre;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
}
