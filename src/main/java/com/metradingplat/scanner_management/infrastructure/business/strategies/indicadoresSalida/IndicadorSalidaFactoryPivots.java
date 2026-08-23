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
 * entrada. Solo configuracion y etiquetado por ahora: el calculo real se
 * implementa en signal-processing-service, siguiendo el mismo diseno que
 * PivotsAlpaca (busca picos/valles dentro de un rango de precio = N x ATR
 * alrededor del precio actual; "fuerte" = pivote del lado correcto del precio,
 * "debil" = respaldo automatico cuando faltan fuertes -- no es una opcion que
 * el usuario elija, por eso no hay parametro para eso aqui).
 */
@Component
public class IndicadorSalidaFactoryPivots implements IIndicadorSalidaFactory {

    // Solo 1D por ahora -- se deja como lista (no una constante suelta) para
    // no tener que rehacer el parametro cuando se habilite mas de un
    // timeframe mas adelante.
    private static final List<EnumTimeframe> TIMEFRAMES_PERMITIDOS = List.of(EnumTimeframe._1D);
    private static final int LONGITUD_VELAS_MIN = 1;
    private static final int LONGITUD_VELAS_MAX = 10;
    private static final float SLIP_RATIO_MIN = 0f;
    private static final float SLIP_RATIO_MAX = 1f;
    private static final int LONGITUD_ATR_MIN = 2;
    private static final int LONGITUD_ATR_MAX = 100;
    private static final int ANIOS_HISTORICO_MIN = 1;
    private static final int ANIOS_HISTORICO_MAX = 10;
    private static final int NUMERO_PIVOTES_MIN = 1;
    private static final int NUMERO_PIVOTES_MAX = 10;

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
        parametros.add(this.crearParametroLongitudAtr(
                (ValorInteger) valoresSeleccionados.get(EnumParametroIndicadorSalida.LONGITUD_ATR_PIVOTS_SALIDA)));
        parametros.add(this.crearParametroAniosHistorico(
                (ValorInteger) valoresSeleccionados.get(EnumParametroIndicadorSalida.ANIOS_HISTORICO_PIVOTS_SALIDA)));
        parametros.add(this.crearParametroNumeroPivotes(
                (ValorInteger) valoresSeleccionados.get(EnumParametroIndicadorSalida.NUMERO_PIVOTES_PIVOTS_SALIDA)));

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

    // Periodo del ATR usado para escalar tanto el rango de busqueda de
    // pivotes como el slip ratio -- 14 es el mismo default que PivotsAlpaca
    // (y el que ya usa el resto de la plataforma para ATR, ver
    // FiltroFactoryATR).
    private ParametroIndicadorSalida crearParametroLongitudAtr(ValorInteger valorUsuario) {
        Integer longitud = valorUsuario != null && valorUsuario.getValor() != null ? valorUsuario.getValor() : 14;
        ValorInteger valor = new ValorInteger(
                EnumParametroIndicadorSalida.LONGITUD_ATR_PIVOTS_SALIDA.getEtiqueta(),
                EnumTipoValor.INTEGER,
                longitud);
        return new ParametroIndicadorSalida(
                EnumParametroIndicadorSalida.LONGITUD_ATR_PIVOTS_SALIDA,
                EnumParametroIndicadorSalida.LONGITUD_ATR_PIVOTS_SALIDA.getEtiqueta(),
                valor,
                new ArrayList<>());
    }

    // Cuantos anios de velas D1 traer para buscar pivotes -- separado del
    // timeframe: el timeframe dice la escala de la vela, esto dice cuanto
    // historial escanear.
    private ParametroIndicadorSalida crearParametroAniosHistorico(ValorInteger valorUsuario) {
        Integer anios = valorUsuario != null && valorUsuario.getValor() != null ? valorUsuario.getValor() : 5;
        ValorInteger valor = new ValorInteger(
                EnumParametroIndicadorSalida.ANIOS_HISTORICO_PIVOTS_SALIDA.getEtiqueta(),
                EnumTipoValor.INTEGER,
                anios);
        return new ParametroIndicadorSalida(
                EnumParametroIndicadorSalida.ANIOS_HISTORICO_PIVOTS_SALIDA,
                EnumParametroIndicadorSalida.ANIOS_HISTORICO_PIVOTS_SALIDA.getEtiqueta(),
                valor,
                new ArrayList<>());
    }

    // Cuantos niveles de pivote devolver por lado (soporte/resistencia) --
    // equivalente a number_pivots en PivotsAlpaca. 1 alcanza para una sola
    // posicion abierta (el pivote mas cercano a cada lado); mayor a 1 sirve
    // para elegir un SL/TP mas conservador entre varios candidatos.
    private ParametroIndicadorSalida crearParametroNumeroPivotes(ValorInteger valorUsuario) {
        Integer numero = valorUsuario != null && valorUsuario.getValor() != null ? valorUsuario.getValor() : 1;
        ValorInteger valor = new ValorInteger(
                EnumParametroIndicadorSalida.NUMERO_PIVOTES_PIVOTS_SALIDA.getEtiqueta(),
                EnumTipoValor.INTEGER,
                numero);
        return new ParametroIndicadorSalida(
                EnumParametroIndicadorSalida.NUMERO_PIVOTES_PIVOTS_SALIDA,
                EnumParametroIndicadorSalida.NUMERO_PIVOTES_PIVOTS_SALIDA.getEtiqueta(),
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

        this.validarRangoInteger(errores, valoresSeleccionados, EnumParametroIndicadorSalida.LONGITUD_VELAS_PIVOTS_SALIDA,
                LONGITUD_VELAS_MIN, LONGITUD_VELAS_MAX);
        this.validarRangoInteger(errores, valoresSeleccionados, EnumParametroIndicadorSalida.LONGITUD_ATR_PIVOTS_SALIDA,
                LONGITUD_ATR_MIN, LONGITUD_ATR_MAX);
        this.validarRangoInteger(errores, valoresSeleccionados, EnumParametroIndicadorSalida.ANIOS_HISTORICO_PIVOTS_SALIDA,
                ANIOS_HISTORICO_MIN, ANIOS_HISTORICO_MAX);
        this.validarRangoInteger(errores, valoresSeleccionados, EnumParametroIndicadorSalida.NUMERO_PIVOTES_PIVOTS_SALIDA,
                NUMERO_PIVOTES_MIN, NUMERO_PIVOTES_MAX);

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

    private void validarRangoInteger(List<ResultadoValidacionIndicadorSalida> errores,
            Map<EnumParametroIndicadorSalida, Valor> valoresSeleccionados,
            EnumParametroIndicadorSalida enumParametro, int min, int max) {
        Valor valor = valoresSeleccionados.get(enumParametro);
        if (valor instanceof ValorInteger valorInteger
                && (valorInteger.getValor() == null || valorInteger.getValor() < min || valorInteger.getValor() > max)) {
            errores.add(new ResultadoValidacionIndicadorSalida(this.enumIndicadorSalida, enumParametro,
                    "validation.parameter.invalid.range", min, max));
        }
    }
}
