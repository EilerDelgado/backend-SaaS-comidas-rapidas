# Arquitectura del Backend

El sistema utiliza una arquitectura basada en capas clásica de Spring Boot (Controller, Service, Repository) combinada con el patrón DTO.

## Capas principales

1. **Controllers (`com.saas.app_de_comida.controller`)**
   - Son el punto de entrada de las peticiones HTTP REST.
   - Reciben peticiones en formato JSON, mapeadas a Request DTOs.
   - Delegan toda la lógica de negocio a la capa de Servicios.
   - Devuelven Response DTOs.
   - **Regla estricta:** Nunca devuelven ni reciben directamente entidades de base de datos.

2. **Services (`com.saas.app_de_comida.service`)**
   - Contienen la lógica de negocio y las validaciones de reglas.
   - Son los encargados de transformar DTOs a Entidades y viceversa (utilizando clases de Mapeo).
   - Manejan transacciones (anotación `@Transactional`) especialmente en operaciones complejas como la creación de pedidos.

3. **Repositories (`com.saas.app_de_comida.repository`)**
   - Interfaces que extienden `JpaRepository`.
   - Se encargan de la persistencia e interacción con MySQL.

4. **Models / Entities (`com.saas.app_de_comida.model`)**
   - Clases que representan las tablas en la base de datos (anotadas con `@Entity`).
   - Contienen las relaciones mapeadas mediante JPA (ej. `@ManyToOne`, `@OneToMany`).

## Manejo de Excepciones

Los errores están centralizados usando un `@RestControllerAdvice` (en `com.saas.app_de_comida.exception.GlobalExceptionHandler`). Esto permite devolver una estructura de error JSON uniforme ante distintas situaciones:

* **400 Bad Request:** Validaciones fallidas de Jakarta Validation (ej. campos vacíos, correos inválidos) o lógica de negocio inválida.
* **404 Not Found:** Cuando un recurso (Producto, Categoría, Usuario, etc.) no existe (`ResourceNotFoundException`).
* **409 Conflict:** Operaciones que violan integridad, como duplicidad de correos (`DuplicateResourceException`).

## DTOs y Validación

Cada operación que reciba datos desde el cliente cuenta con un DTO específico validado mediante anotaciones de `jakarta.validation` (`@NotBlank`, `@NotNull`, `@Positive`, etc.). Las validaciones se ejecutan automáticamente en el Controller antes de llegar al Service.
