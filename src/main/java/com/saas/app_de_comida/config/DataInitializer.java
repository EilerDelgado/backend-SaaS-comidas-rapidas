package com.saas.app_de_comida.config;

import com.saas.app_de_comida.model.Restaurante;
import com.saas.app_de_comida.model.Usuario;
import com.saas.app_de_comida.model.enums.Role;
import com.saas.app_de_comida.repository.IRestauranteRepository;
import com.saas.app_de_comida.repository.IUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final IUsuarioRepository usuarioRepository;
    private final IRestauranteRepository restauranteRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (usuarioRepository.count() == 0) {
            // Crear un restaurante por defecto
            Restaurante restaurante = new Restaurante();
            restaurante.setNombre("Restaurante Principal");
            restaurante.setDireccion("Calle 123");
            restaurante.setTelefono("123456789");
            restaurante.setActivo(true);
            restauranteRepository.save(restaurante);

            // Crear un Super Admin por defecto
            Usuario superAdmin = new Usuario();
            superAdmin.setNombre("Super Administrador");
            superAdmin.setCorreo("admin@admin.com");
            superAdmin.setContrasena(passwordEncoder.encode("admin123"));
            superAdmin.setRol(Role.SUPER_ADMIN);
            superAdmin.setRestaurante(restaurante);
            usuarioRepository.save(superAdmin);

            System.out.println("==================================================");
            System.out.println("Se ha creado un usuario administrador por defecto:");
            System.out.println("Correo: admin@admin.com");
            System.out.println("Contraseña: admin123");
            System.out.println("==================================================");
        }
    }
}
