package com.saas.app_de_comida.repository;

import com.saas.app_de_comida.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPedidoRepository extends JpaRepository<Pedido, String> {
    List<Pedido> findByRestauranteId(Integer restauranteId);
    List<Pedido> findByClienteId(Integer clienteId);
    List<Pedido> findByClienteIdAndRestauranteId(Integer clienteId, Integer restauranteId);
}
