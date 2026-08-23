package com.metradingplat.scanner_management.infrastructure.business.strategies.indicadoresSalida;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.metradingplat.scanner_management.domain.enums.EnumIndicadorSalida;
import com.metradingplat.scanner_management.domain.enums.EnumParametroIndicadorSalida;
import com.metradingplat.scanner_management.domain.enums.EnumTipoValor;
import com.metradingplat.scanner_management.domain.enums.valores.EnumTimeframe;
import com.metradingplat.scanner_management.domain.enums.valores.IEnumValores;
import com.metradingplat.scanner_management.domain.models.IndicadorSalida;
import com.metradingplat.scanner_management.domain.models.ParametroIndicadorSalida;
import com.metradingplat.scanner_management.domain.models.Valor;
import com.metradingplat.scanner_management.domain.models.ValorFloat;
import com.metradingplat.scanner_management.domain.models.ValorInteger;
import com.metradingplat.scanner_management.domain.models.ValorString;
import com.metradingplat.scanner_management.infrastructure.business.strategies.IIndicadorSalidaFactory;
import com.metradingplat.scanner_management.infrastructure.business.validation.ResultadoValidacionIndicadorSalida;

/**
 * Indicador de salida basado en pivotes de precio (swing highs/lows) --
 * calcula niveles de stop loss / take profit, no participa en el escaneo de
 * entrada. Solo configuracion y etiquetado por ahora: el calculo real
 * (deteccion vectorizada de pivotes con rango ATR, fusion de pivotes cercanos
 * por slipRatio) se implementa cuando se defina como se conecta con
 * ordenes/posiciones.
 */
@Component
public class IndicadorSalidaFactoryPivots implements IIndicadorSalidaFactory {

    private static final List<EnumTimeframe> TIMEFRAMES_PERMITIDOS = Arrays.asList(
            EnumTimeframe._1D, EnumTimeframe._1W, EnumTimeframe._1MO);
    private static final int LONGITUD_VELAS_MIN = 1;
    private static final int LONGITUD_VELAS_MAX = 10;
    private static final float SLIP_RATIO_MIN = 0f;
    private static final float SLIP_RATIO_MAX = 1f;

    private final EnumIndicadorSalida enumIndicadorSalida = EnumIndicadorSalida.PIVOTS;

    @Override
    public EnumIndicadorSalida obtenerEnumIndicadorSalida() {
        return this.enumIndicadorSalida;
    }

    @Override
    public IndicadorSalida obtenerInformacionIndicadorSalida() {
        IndicadorSalida indicador = new IndicadorSalida();
        indicador.setEnumIndicadorSalida(this.enumIndicadorSalida);
        indicador.setEtiquetaNombre(this.enumIndicadorSalida.getEtiquetaNombre());
        indicador.setEtiquetaDescripcion(this.enumIndicadorSalida.getEtiquetaDescripcion());
        return indicador;
    }

    @Override
    public IndicadorSalida obtenerIndicadorSalida() {
        return this.obtenerIndicadorSalida(new HashMap<>());
    }

    @Override
    public IndicadorSalida obtenerIndicadorSalida(Map<EnumParametroIndicadorSalida, Valor> valoresSeleccionados) {
        IndicadorSalida indicador = this.obtenerInformacionIndicadorSalida();

        List<ParametroIndicadorSalida> parametros = new ArrayList<>();
        parametros.add(this.crearParametroTimeframe(
                (ValorString) valoresSeleccionados.get(EnumParametroIndicadorSalida.TIMEFRAME_PIVOTS_SALIDA)));
        parametros.add(this.crearParametroLongitudVelas(
                (ValorInteger) valoresSeleccionados.get(EnumParametroIndicadorSalida.LONGITUD_VELAS_PIVOTS_SALIDA)));
        parametros.add(this.crearParametroSlipRatio(
                (ValorFloat) valoresSeleccionados.get(EnumParametroIndicadorSalida.SLIP_RATIO_PIVOTS_SALIDA)));

        indicador.setParametros(parametros);
        return indicador;
    }

    private List<Valor> obtenerOpcionesTimeframe() {
        return TIMEFRAMES_PERMITIDOS.stream()
                .map(e -> (Valor) new ValorString(e.getEtiqueta(), EnumTipoValor.STRING, e.getName()))
                .collect(Collectors.toList());
    }

    private ParametroIndicadorSalida crearParametroTimeframe(ValorString valorUsuario) {
        EnumTimeframe enumValor = valorUsuario != null
                ? EnumTimeframe.valueOf(valorUsuario.getValor())
                : EnumTimeframe._1D;
        ValorString valor = new ValorString(enumValor.getEtiqueta(), EnumTipoValor.STRING, enumValor.name());
        return new ParametroIndicadorSalida(
                EnumParametroIndicadorSalida.TIMEFRAME_PIVOTS_SALIDA,
                EnumParametroIndicadorSalida.TIMEFRAME_PIVOTS_SALIDA.getEtiqueta(),
                valor,
                this.obtenerOpcionesTimeframe());
    }

    private ParametroIndicadorSalida crearParametroLongitudVelas(ValorInteger valorUsuario) {
        Integer longitud = valorUsuario != null && valorUsuario.getValor() != null ? valorUsuario.getValor() : 2;
        ValorInteger valor = new ValorInteger(
                EnumParametroIndicadorSalida.LONGITUD_VELAS_PIVOTS_SALIDA.getEtiqueta(),
                EnumTipoValor.INTEGER,
                longitud);
        return new ParametroIndicadorSalida(
                EnumParametroIndicadorSalida.LONGITUD_VELAS_PIVOTS_SALIDA,
                EnumParametroIndicadorSalida.LONGITUD_VELAS_PIVOTS_SALIDA.getEtiqueta(),
                valor,
                new ArrayList<>());
    }

    private ParametroIndicadorSalida crearParametroSlipRatio(ValorFloat valorUsuario) {
        Float slipRatio = valorUsuario != null && valorUsuario.getValor() != null ? valorUsuario.getValor() : 0.1f;
        ValorFloat valor = new ValorFloat(
                EnumParametroIndicadorSalida.SLIP_RATIO_PIVOTS_SALIDA.getEtiqueta(),
                EnumTipoValor.FLOAT,
                slipRatio);
        return new ParametroIndicadorSalida(
                EnumParametroIndicadorSalida.SLIP_RATIO_PIVOTS_SALIDA,
                EnumParametroIndicadorSalida.SLIP_RATIO_PIVOTS_SALIDA.getEtiqueta(),
                valor,
                new ArrayList<>());
    }

    @Override
    public List<ResultadoValidacionIndicadorSalida> validarValoresSeleccionados(
            Map<EnumParametroIndicadorSalida, Valor> valoresSeleccionados) {
        List<ResultadoValidacionIndicadorSalida> errores = new ArrayList<>();

        Valor timeframe = valoresSeleccionados.get(EnumParametroIndicadorSalida.TIMEFRAME_PIVOTS_SALIDA);
        if (timeframe instanceof ValorString valorString) {
            boolean valido = TIMEFRAMES_PERMITIDOS.stream()
                    .map(IEnumValores::getName)
                    .anyMatch(nombre -> nombre.equals(valorString.getValor()));
            if (!valido) {
                errores.add(new ResultadoValidacionIndicadorSalida(this.enumIndicadorSalida,
                        EnumParametroIndicadorSalida.TIMEFRAME_PIVOTS_SALIDA,
                        "validation.parameter.invalid.options"));
            }
        }

        Valor longitudVelas = valoresSeleccionados.get(EnumParametroIndicadorSalida.LONGITUD_VELAS_PIVOTS_SALIDA);
        if (longitudVelas instanceof ValorInteger valorInteger
                && (valorInteger.getValor() == null
                        || valorInteger.getValor() < LONGITUD_VELAS_MIN
                        || valorInteger.getValor() > LONGITUD_VELAS_MAX)) {
            errores.add(new ResultadoValidacionIndicadorSalida(this.enumIndicadorSalida,
                    EnumParametroIndicadorSalida.LONGITUD_VELAS_PIVOTS_SALIDA,
                    "validation.parameter.invalid.range", LONGITUD_VELAS_MIN, LONGITUD_VELAS_MAX));
        }

        Valor slipRatio = valoresSeleccionados.get(EnumParametroIndicadorSalida.SLIP_RATIO_PIVOTS_SALIDA);
        if (slipRatio instanceof ValorFloat valorFloat
                && (valorFloat.getValor() == null
                        || valorFloat.getValor() < SLIP_RATIO_MIN
                        || valorFloat.getValor() > SLIP_RATIO_MAX)) {
            errores.add(new ResultadoValidacionIndicadorSalida(this.enumIndicadorSalida,
                    EnumParametroIndicadorSalida.SLIP_RATIO_PIVOTS_SALIDA,
                    "validation.parameter.invalid.range", SLIP_RATIO_MIN, SLIP_RATIO_MAX));
        }

        return errores;
    }
}
