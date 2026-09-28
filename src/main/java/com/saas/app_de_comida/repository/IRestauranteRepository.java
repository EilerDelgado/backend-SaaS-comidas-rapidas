package com.saas.app_de_comida.repository;

import com.saas.app_de_comida.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRestauranteRepository extends JpaRepository<Restaurante, Integer> {
}
