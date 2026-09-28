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
El proyecto se encuentra en una **etapa inicial (Fase de auditoría y arquitectura)**. Las entidades base ya existen y se están construyendo progresivamente las funcionalidades principales.

### Funcionalidades implementadas:
* Estructura base de entidades (Usuario, Producto, Categoria, Pedido, DetallePedido, Caja, Restaurante).
* Interfaces de repositorios (JPA).
* Servicios CRUD iniciales para Categoria, Producto y Usuario.
* Manejo global de excepciones (`@RestControllerAdvice`).
* Configuración básica de Seguridad (CORS para React y acceso público temporal).

## Información importante
* Consulta la carpeta `docs/` para detalles específicos sobre arquitectura, base de datos y seguridad.