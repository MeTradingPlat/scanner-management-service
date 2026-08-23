package com.metradingplat.scanner_management.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Bolsa plana de nombres de parametros para indicadores de salida, mismo
 * criterio que EnumParametro para los filtros: un nombre por parametro,
 * compartido entre todas las implementaciones de IIndicadorSalidaFactory.
 */
@Getter
@AllArgsConstructor
public enum EnumParametroIndicadorSalida {
    TIMEFRAME_PIVOTS_SALIDA("parameter.indicatorExit.pivots.timeframe"),
    LONGITUD_VELAS_PIVOTS_SALIDA("parameter.indicatorExit.pivots.longitudVelas"),
    SLIP_RATIO_PIVOTS_SALIDA("parameter.indicatorExit.pivots.slipRatio");

    private final String etiqueta;
}
