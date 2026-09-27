package com.saas.app_de_comida.exception;

/**
 * Se lanza cuando se intenta crear un recurso que ya existe (violación de unicidad).
 * El GlobalExceptionHandler la convierte en HTTP 409.
 *
 * Uso:
 *   throw new DuplicateResourceException("Ya existe una categoría con ese nombre");
 */
public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String message) {
        super(message);
    }
}
