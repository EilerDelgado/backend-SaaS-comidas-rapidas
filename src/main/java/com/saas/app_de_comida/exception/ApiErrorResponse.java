package com.saas.app_de_comida.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Estructura estándar para todas las respuestas de error de la API.
 *
 * Ejemplo de respuesta:
 * {
 *   "status": 404,
 *   "message": "Producto no encontrado",
 *   "timestamp": "2026-09-25T17:53:00"
 * }
 */
@Getter
@AllArgsConstructor
public class ApiErrorResponse {

    private int status;
    private String message;
    private LocalDateTime timestamp;

    public ApiErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
