# Seguridad

El sistema implementará seguridad basada en tokens (JWT) y Spring Security.

## Estado Actual (Temporal)

Actualmente, el proyecto cuenta con una configuración temporal (`SecurityConfig.java`) orientada a facilitar el desarrollo inicial (Fases 1-4):
* Todas las rutas HTTP (`/**`) están permitidas sin necesidad de autenticación.
* El mecanismo CSRF está deshabilitado.
* Está configurado el CORS para permitir peticiones desde aplicaciones frontend de desarrollo local (`http://localhost:3000`, `http://localhost:5173`).

**Esta configuración será sustituida en la Fase 5** por un flujo real de autenticación.

## Roles del Sistema

El sistema categoriza los permisos mediante la enumeración `Role`, orientada tanto a las operaciones de la plataforma SaaS (online) como del Punto de Venta (POS):

* `SUPER_ADMIN`: Administrador de toda la plataforma SaaS, encargado de gestionar los distintos restaurantes.
* `ADMIN`: Cajero o administrador local de un Restaurante físico. Puede manejar cajas, modificar inventarios, crear productos y registrar pedidos presenciales.
* `COCINA`: Usuario operativo (cocinero/preparador). Su alcance está limitado a recibir pedidos y actualizar los estados de los mismos.
* `CLIENTE`: Usuario final que se registra en la plataforma para realizar compras online.

## Prácticas de Seguridad

* **Nunca exponer contraseñas:** Ningún endpoint que devuelva información de Usuarios debe incluir la contraseña (hasheada o en texto plano). Se deben usar DTOs de Respuesta para filtrar esta información.
* **Validación en backend:** Todos los cálculos críticos (como subtotales, totales de pedidos e impuestos) se realizan obligatoriamente en el servidor. Nunca se confía en un cálculo de precios enviado desde el cliente web.
