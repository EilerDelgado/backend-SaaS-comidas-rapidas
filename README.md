# App de Comida (SaaS & POS)

Backend para un sistema Multi-tenant (SaaS) y Punto de Venta (POS) diseñado para restaurantes y tiendas de comida.

## ¿Qué problema resuelve?
Este sistema permite a múltiples restaurantes registrarse y operar sus ventas de manera independiente (SaaS), brindando tanto un portal para clientes online como una interfaz de Punto de Venta (POS) para uso en mostrador y cocina, facilitando así la gestión integral de pedidos, productos y facturación.

## Tecnologías principales
* **Java 21**
* **Spring Boot 3.4.4** (Web, Data JPA, Security, Validation, WebSocket)
* **MySQL** (Base de datos principal)
* **Lombok** (Reducción de código repetitivo)
* **Maven** (Gestión de dependencias y construcción)

## Requisitos
* JDK 21
* MySQL Server
* Maven (opcional)

## Configuración y ejecución
1. Clona el repositorio.
2. Asegúrate de tener una base de datos con el nombre que esta cofigurada
3. Inicia a correr el proyecto
4. El proyecto iniciará

## Estructura general y Arquitectura
El sistema implementa una arquitectura multicapa estándar de Spring Boot:
* **Controllers** (`/controller`): Exponen la API REST.
* **Services** (`/service`): Contienen la lógica de negocio.
* **Repositories** (`/repository`): Interfaces JPA para acceso a datos.
* **Models/Entities** (`/model`): Entidades de persistencia (MySQL).
* **DTOs** (`/dto`): Objetos de transferencia de datos utilizados en las peticiones y respuestas HTTP.

**Nota técnica:** Las entidades nunca se exponen directamente a través de los controllers. Se utilizan DTOs validados para transferir datos hacia y desde la capa de servicio.

## Estado actual
El proyecto ha completado la **Fase 5 (Seguridad, Autenticación JWT y Gestión de Roles)**. Se ha establecido una arquitectura sólida, CRUDs fundamentales y un sistema de control de accesos jerárquico basado en roles (SaaS).

### Funcionalidades implementadas:
* Estructura base de entidades (Usuario, Producto, Categoria, Pedido, DetallePedido, Caja, Restaurante).
* Servicios CRUD validados (Jakarta Validation) para Categoria, Producto y Usuario usando DTOs.
* Manejo global de excepciones (`@RestControllerAdvice`).
* **Seguridad y Autenticación (JWT):** Implementación de Spring Security sin estado (stateless) usando JSON Web Tokens.
* **Control de Accesos por Roles:** 
  * Registro público para `CLIENTE`.
  * Creación exclusiva de `ADMIN` por el `SUPER_ADMIN`.
  * Creación exclusiva de `COCINA` por parte del `ADMIN` local, inyectando automáticamente el contexto multi-tenant (ID de Restaurante).
* Inicialización de datos de prueba (`DataInitializer`) para facilitar el testing.
* Configuración de CORS preparada para consumir desde frontend (React/Vite).

## Información importante
* Consulta la carpeta `docs/` para detalles específicos sobre arquitectura, base de datos y seguridad.