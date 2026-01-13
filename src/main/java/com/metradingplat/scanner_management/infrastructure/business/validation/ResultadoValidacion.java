package com.metradingplat.scanner_management.infrastructure.business.validation;

import com.metradingplat.scanner_management.domain.enums.EnumFiltro;
import com.metradingplat.scanner_management.domain.enums.EnumParametro;

/**
 * Record que representa el resultado de validación de un parámetro de filtro.
 * Incluye el contexto completo del filtro al que pertenece el parámetro,
 * permitiendo una trazabilidad precisa de errores en el frontend.
 *
 * @param enumFiltro    El tipo de filtro al que pertenece el parámetro (ej:
 *                      RSI, VOLUME)
 * @param enumParametro El tipo de parámetro que falló la validación (ej:
 *                      PERIODO_RSI, CONDICION)
 * @param mensaje       Clave del mensaje de error internacionalizable
 * @param args          Argumentos opcionales para la internacionalización del
 *                      mensaje
 */
public record ResultadoValidacion(
                EnumFiltro enumFiltro,
                EnumParametro enumParametro,
                String mensaje,
                Object... args) {
}
