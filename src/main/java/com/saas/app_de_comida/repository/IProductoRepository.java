package com.saas.app_de_comida.repository;

import com.saas.app_de_comida.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductoRepository extends JpaRepository<Producto, Integer> {
}
