package com.saas.app_de_comida.service;

import com.saas.app_de_comida.dto.pedido.EstadoPedidoRequestDTO;
import com.saas.app_de_comida.dto.pedido.PedidoRequestDTO;
import com.saas.app_de_comida.dto.pedido.PedidoResponseDTO;
import com.saas.app_de_comida.model.Usuario;

import java.util.List;

public interface PedidoService {

    PedidoResponseDTO createPedido(PedidoRequestDTO request, Usuario usuarioActual);

    PedidoResponseDTO findByCodigo(String codigo);

    List<PedidoResponseDTO> findByRestauranteId(Integer restauranteId);

    List<PedidoResponseDTO> findByClienteId(Integer clienteId);

    PedidoResponseDTO changeEstado(String codigo, EstadoPedidoRequestDTO request, Usuario usuarioActual);
}
