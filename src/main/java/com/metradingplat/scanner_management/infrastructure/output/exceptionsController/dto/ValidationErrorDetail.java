package com.metradingplat.scanner_management.infrastructure.output.exceptionsController.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa el detalle de un error de validación de parámetro.
 * Sigue el principio de diseño de DTOs específicos para la capa de
 * presentación.
 *
 * Esta clase permite enviar al frontend información precisa sobre:
 * - El filtro al que pertenece el error
 * - El parámetro específico que falló la validación
 * - El mensaje de error internacionalizado
 *
 * Beneficios:
 * - Permite al frontend identificar exactamente dónde mostrar cada error
 * - Facilita la trazabilidad y debugging de errores de validación
 * - Mejora la UX al mostrar errores contextualizados al usuario
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorDetail {

    /**
     * Nombre del filtro al que pertenece el parámetro que falló.
     * Ejemplo: "RSI", "VOLUME", "ATR"
     */
    private String filtro;

    /**
     * Nombre del parámetro que falló la validación.
     * Ejemplo: "PERIODO_RSI", "CONDICION", "TIPO_VOLUMEN"
     */
    private String parametro;

    /**
     * Mensaje de error internacionalizado y listo para mostrar al usuario.
     * El backend ya ha procesado la internacionalización.
     */
    private String mensaje;

    /**
     * Campo opcional para indicar el índice del filtro si hay múltiples filtros del
     * mismo tipo.
     * Útil cuando un escaner tiene 2+ filtros RSI, por ejemplo.
     * Si es null, se asume que es el primero/único filtro de ese tipo.
     */
    private Integer filtroIndex;
}
