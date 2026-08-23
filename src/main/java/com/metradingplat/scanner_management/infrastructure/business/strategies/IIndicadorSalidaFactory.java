package com.metradingplat.scanner_management.infrastructure.business.strategies;

import java.util.List;
import java.util.Map;

import com.metradingplat.scanner_management.domain.enums.EnumIndicadorSalida;
import com.metradingplat.scanner_management.domain.enums.EnumParametroIndicadorSalida;
import com.metradingplat.scanner_management.domain.models.IndicadorSalida;
import com.metradingplat.scanner_management.domain.models.Valor;
import com.metradingplat.scanner_management.infrastructure.business.validation.ResultadoValidacionIndicadorSalida;

/**
 * Contrato de factoria para un tipo de indicador de salida (stop loss / take
 * profit). Mismo patron Abstract Factory que IFiltroFactory, pero para un
 * catalogo independiente: un indicador de salida no evalua una condicion
 * booleana de match, define como calcular un nivel de precio de salida.
 *
 * @see GestorIndicadorSalidaFactory
 */
public interface IIndicadorSalidaFactory {

    EnumIndicadorSalida obtenerEnumIndicadorSalida();

    IndicadorSalida obtenerInformacionIndicadorSalida();

    IndicadorSalida obtenerIndicadorSalida();

    IndicadorSalida obtenerIndicadorSalida(Map<EnumParametroIndicadorSalida, Valor> valoresSeleccionados);

    List<ResultadoValidacionIndicadorSalida> validarValoresSeleccionados(
            Map<EnumParametroIndicadorSalida, Valor> valoresSeleccionados);
}
