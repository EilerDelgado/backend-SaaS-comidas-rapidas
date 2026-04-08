package com.saas.app_de_comida.repository;

import com.saas.app_de_comida.model.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDetallePedidoRepository extends JpaRepository<DetallePedido, Integer> {
}
