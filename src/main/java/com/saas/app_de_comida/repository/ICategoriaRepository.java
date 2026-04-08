package com.saas.app_de_comida.repository;

import com.saas.app_de_comida.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoriaRepository extends JpaRepository<Categoria, Integer> {
}
