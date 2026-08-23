package com.metradingplat.scanner_management.application.output;

import java.util.List;
import java.util.Map;

import com.metradingplat.scanner_management.domain.enums.EnumIndicadorSalida;
import com.metradingplat.scanner_management.domain.enums.EnumParametroIndicadorSalida;
import com.metradingplat.scanner_management.domain.models.IndicadorSalida;
import com.metradingplat.scanner_management.domain.models.Valor;
import com.metradingplat.scanner_management.infrastructure.business.validation.ResultadoValidacionIndicadorSalida;

/**
 * Port para gestionar estrategias de indicadores de salida (stop loss / take
 * profit). Equivalente a GestorEstrategiaFiltroIntPort para el catalogo de
 * filtros de escaneo.
 */
public interface GestorEstrategiaIndicadorSalidaIntPort {

    List<EnumIndicadorSalida> obtenerTodosLosIndicadores();

    IndicadorSalida obtenerInformacionIndicadorSalida(EnumIndicadorSalida tipoIndicador);

    IndicadorSalida obtenerIndicadorSalidaConValoresPorDefecto(EnumIndicadorSalida tipoIndicador);

    IndicadorSalida crearIndicadorSalidaConValoresSeleccionados(EnumIndicadorSalida tipoIndicador,
            Map<EnumParametroIndicadorSalida, Valor> valores);

    Boolean validarEnumIndicadorSalida(EnumIndicadorSalida tipoIndicador);

    List<ResultadoValidacionIndicadorSalida> validarValoresSeleccionados(EnumIndicadorSalida tipoIndicador,
            Map<EnumParametroIndicadorSalida, Valor> valores);
}
