package com.saas.app_de_comida.repository;

import com.saas.app_de_comida.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {
    boolean existsByCorreo(String correo);
    java.util.Optional<Usuario> findByCorreo(String correo);
}
