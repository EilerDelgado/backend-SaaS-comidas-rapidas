# Seguridad

El sistema implementará seguridad basada en tokens (JWT) y Spring Security.

## Estado Actual: Autenticación JWT y Roles (Fase 5.1 Completada)

El proyecto cuenta con un sistema de seguridad robusto configurado a través de `SecurityConfig.java`, utilizando **Spring Security** y **JSON Web Tokens (JWT)**. 

### Filtros de Seguridad y Endpoints Protegidos
El sistema opera en modo `STATELESS` (sin sesiones de servidor). Las reglas de autorización actuales son:
* **Accesos Públicos (`permitAll`)**:
  * `/api/auth/login`: Para obtener el token JWT.
  * `/api/auth/registro-cliente`: Registro libre inyectando automáticamente el rol `CLIENTE`.
* **Accesos Restringidos por Rol (`hasRole`)**:
  * `/api/usuarios/admin`: Protegido. Solo accesible por un `SUPER_ADMIN`.
  * `/api/usuarios/cocinero`: Protegido. Solo accesible por un `ADMIN` (el cual inyectará automáticamente su propio ID de restaurante al nuevo cocinero).
* **Resto de la API (`authenticated`)**:
  * Cualquier otra petición requiere obligatoriamente enviar un token JWT válido en la cabecera `Authorization: Bearer <token>`.

### Estructura del Token JWT
Al autenticarse, el backend genera un token que contiene `claims` (datos extra) útiles para el frontend, evitando peticiones redundantes. El payload del token contiene:
* `sub` (Subject): Correo del usuario.
* `id_usuario`: ID interno en la base de datos.
* `rol`: Rol del usuario (ej. `CLIENTE`, `ADMIN`).
* `restaurante_id`: ID del restaurante asociado (si aplica).

### Configuración CORS
Está habilitado para permitir peticiones desde clientes web locales (`http://localhost:3000` y `http://localhost:5173`) con credenciales y todos los métodos HTTP principales.

## Roles del Sistema

El sistema categoriza los permisos mediante la enumeración `Role`, orientada tanto a las operaciones de la plataforma SaaS (online) como del Punto de Venta (POS):

* `SUPER_ADMIN`: Administrador de toda la plataforma SaaS, encargado de gestionar los distintos restaurantes.
* `ADMIN`: Cajero o administrador local de un Restaurante físico. Puede manejar cajas, modificar inventarios, crear productos y registrar pedidos presenciales.
* `COCINA`: Usuario operativo (cocinero/preparador). Su alcance está limitado a recibir pedidos y actualizar los estados de los mismos.
* `CLIENTE`: Usuario final que se registra en la plataforma para realizar compras online.

## Prácticas de Seguridad

* **Nunca exponer contraseñas:** Ningún endpoint que devuelva información de Usuarios debe incluir la contraseña (hasheada o en texto plano). Se deben usar DTOs de Respuesta para filtrar esta información.
* **Validación en backend:** Todos los cálculos críticos (como subtotales, totales de pedidos e impuestos) se realizan obligatoriamente en el servidor. Nunca se confía en un cálculo de precios enviado desde el cliente web.
