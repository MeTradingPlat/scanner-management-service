package com.metradingplat.scanner_management.infrastructure.business.validation;

import com.metradingplat.scanner_management.domain.enums.EnumIndicadorSalida;
import com.metradingplat.scanner_management.domain.enums.EnumParametroIndicadorSalida;

/**
 * Equivalente a ResultadoValidacion pero para el catalogo de indicadores de
 * salida -- separado porque referencia EnumIndicadorSalida/
 * EnumParametroIndicadorSalida en vez de EnumFiltro/EnumParametro.
 */
public record ResultadoValidacionIndicadorSalida(
                EnumIndicadorSalida enumIndicadorSalida,
                EnumParametroIndicadorSalida enumParametroIndicadorSalida,
                String mensaje,
                Object... args) {
}
