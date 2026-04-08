# backend-SaaS-comidas-rapidas

Backend de una aplicación SaaS para restaurante de comidas rápidas. Este es mi primer proyecto real con Spring Boot, lo estoy construyendo para aprender cómo funciona una arquitectura backend completa.

## ¿Qué hace?

Maneja tres tipos de usuarios:
- **CLIENTE** — puede hacer pedidos online
- **COCINA** — recibe los pedidos en tiempo real
- **ADMIN** — gestiona el menú y los pedidos presenciales

## Stack

- Java 17
- Spring Boot 3.4.4
- MySQL
- WebSockets + STOMP (para tiempo real)

## Estado actual

Todavía en construcción. Por ahora tengo lista la capa de modelos y repositorios. Voy avanzando capa por capa.

## Estructura del proyecto

- src/main/java/com/saas/
- ├── model/
- │   ├── enums/
- ├── repository/
- ├── service/        
- ├── controller/
- ├── dto/
- └── mapper/

> El frontend va en un repositorio separado.