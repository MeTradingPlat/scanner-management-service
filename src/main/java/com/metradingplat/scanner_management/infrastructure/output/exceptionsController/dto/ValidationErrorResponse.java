package com.metradingplat.scanner_management.infrastructure.output.exceptionsController.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO de respuesta para errores de validación estructurados.
 * Incluye tanto la información del error base como los detalles específicos de
 * validación.
 *
 * Esta estructura sigue las mejores prácticas de:
 * - RFC 7807 (Problem Details for HTTP APIs)
 * - Separación de concerns: error general vs errores específicos de validación
 * - Extensibilidad: permite añadir más campos sin romper compatibilidad
 * - Composition over Inheritance: mejor para usar con Lombok Builder
 *
 * Estructura de respuesta:
 * {
 * "codigoError": "GC-0005",
 * "mensaje": "Error de validación en filtros",
 * "codigoHttp": 400,
 * "url": "/api/escaner/filtro/escaner/123",
 * "metodo": "POST",
 * "erroresValidacion": [
 * {
 * "filtro": "RSI",
 * "parametro": "PERIODO_RSI",
 * "mensaje": "El período debe ser mayor que 0",
 * "filtroIndex": 0
 * }
 * ]
 * }
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorResponse {

    /**
     * Código de error de la aplicación (ej: "GC-0005")
     */
    private String codigoError;

    /**
     * Mensaje de error internacionalizado listo para mostrar al usuario
     */
    private String mensaje;

    /**
     * Código de estado HTTP (ej: 400, 404, 500)
     */
    private Integer codigoHttp;

    /**
     * URL de la petición que generó el error
     */
    private String url;

    /**
     * Método HTTP de la petición (GET, POST, PUT, DELETE)
     */
    private String metodo;

    /**
     * Lista de errores de validación detallados.
     * Cada elemento indica exactamente qué filtro y parámetro falló la validación.
     */
    @Builder.Default
    private List<ValidationErrorDetail> erroresValidacion = new ArrayList<>();
}
