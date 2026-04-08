package com.saas.app_de_comida.repository;

import com.saas.app_de_comida.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPedidoRepository extends JpaRepository<Pedido, Integer> {
}
