package com.metradingplat.scanner_management.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Indicadores de salida: definen niveles de precio (stop loss / take profit)
 * para posiciones ya abiertas -- a diferencia de EnumFiltro, que decide si un
 * simbolo entra o no en los resultados de un escaneo. Catalogo separado a
 * proposito: su contrato de salida es un precio, no un booleano de match.
 */
@Getter
@AllArgsConstructor
public enum EnumIndicadorSalida {
    PIVOTS("indicatorExit.pivots.name", "indicatorExit.pivots.description");

    private final String etiquetaNombre;
    private final String etiquetaDescripcion;
}
