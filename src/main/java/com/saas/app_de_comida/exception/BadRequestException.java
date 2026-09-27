package com.saas.app_de_comida.exception;

/**
 * Se lanza cuando la petición contiene datos inválidos por reglas de negocio
 * que no se cubren con @Valid (Jakarta Validation).
 * El GlobalExceptionHandler la convierte en HTTP 400.
 *
 * Uso:
 *   throw new BadRequestException("La cantidad debe ser mayor a 0");
 */
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}
