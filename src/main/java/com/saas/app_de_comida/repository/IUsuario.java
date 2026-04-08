package com.saas.app_de_comida.repository;

import com.saas.app_de_comida.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuario extends JpaRepository<Usuario, Integer> {
}
