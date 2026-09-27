package com.saas.app_de_comida.exception;

/**
 * Se lanza cuando un recurso no existe en la base de datos.
 * El GlobalExceptionHandler la convierte en HTTP 404.
 *
 * Uso:
 *   throw new ResourceNotFoundException("Producto", id);
 *   → "Producto con id 5 no encontrado"
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String recurso, Integer id) {
        super(recurso + " con id " + id + " no encontrado");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
