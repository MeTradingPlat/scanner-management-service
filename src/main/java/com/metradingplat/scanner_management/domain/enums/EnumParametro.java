package com.metradingplat.scanner_management.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumParametro {
    // Parámetros Generales de Condición
    CONDICION("parameter.general.condition"),

    // Parámetros de Volumen
    TIPO_VOLUMEN("parameter.volume.tipoVolumen"),
    TIMEFRAME_VOLUME("parameter.volume.timeframe"),
    TIMEFRAME_AVERAGE_VOLUME("parameter.averageVolume.timeframe"),
    TIMEFRAME_RELATIVE_VOLUME_PERCENT("parameter.relativeVolumePercent.timeframe"),
    NUMERO_VELAS_VOLUME_SPIKE("parameter.volumeSpike.numeroVelas"),
    TIMEFRAME_VOLUME_SPIKE("parameter.volumeSpike.timeframe"),
    PROPORCION_VOLUMEN_VOLUME_SPIKE("parameter.volumeSpike.proporcionVolumen"),

    // Parámetros de Precio y Movimiento
    PUNTO_REFERENCIA_CHANGE("parameter.change.puntoReferencia"),
    TIPO_MEDIDA_CHANGE("parameter.change.tipoMedida"),
    TIMEFRAME_PERCENTAGE_CHANGE_PERCENT("parameter.percentageChangePercent.timeframe"),
    FORMATO_GAP_FROM_CLOSE("parameter.gapFromClose.formato"),
    TIMEFRAME_POSITION_IN_RANGE("parameter.positionInRange.timeframe"),
    TIMEFRAME_PERCENTAGE_RANGE_PERCENT("parameter.percentageRangePercent.timeframe"),
    TIMEFRAME_RANGE_DOLLAR("parameter.rangeDollar.timeframe"),
    NIVEL_CRUCE_CROSSING_ABOVE_BELOW("parameter.crossingAboveBelow.nivelCruce"),
    PERIODO_EMA_CROSSING_ABOVE_BELOW("parameter.crossingAboveBelow.periodoEMA"),
    VALOR_HALT("parameter.halt.valor"),

    // Parámetros de Volatilidad
    LONGITUD_ATR("parameter.atr.longitud"),
    MODO_PROMEDIO_MOVIL_ATR("parameter.atr.modoPromedioMovil"),
    TIMEFRAME_ATR("parameter.atr.timeframe"),
    PERIODO_ATR_ATRP("parameter.atrp.periodoATR"),
    TIPO_PROMEDIO_MOVIL_ATRP("parameter.atrp.tipoPromedioMovil"),
    VALOR_PROMEDIO_MOVIL_ATRP("parameter.atrp.valorPromedioMovil"),
    TIMEFRAME_ATRP("parameter.atrp.timeframe"),

    // Parámetros de Momentum e Indicadores Técnicos
    PERIODO_RSI("parameter.rsi.periodoRsi"),
    TIMEFRAME_RSI("parameter.rsi.timeframe"),
    LINEA_REFERENCIA_DISTANCE_FROM_VWAP_EMA_MA("parameter.distanceFromVwapEmaMa.lineaReferencia"),
    MODO_DISTANCIA_DISTANCE_FROM_VWAP_EMA_MA("parameter.distanceFromVwapEmaMa.modoDistancia"),
    PERIODO_LINEA_DISTANCE_FROM_VWAP_EMA_MA("parameter.distanceFromVwapEmaMa.periodoLinea"),
    PERIODO_EMA_BACK_TO_EMA("parameter.backToEmaAlert.periodoEMA"),
    TIMEFRAME_BACK_TO_EMA("parameter.backToEmaAlert.timeframe"),
    THROUGH_EMA_VWAP_LINEA_CRUCE("parameter.throughEmaVwapAlert.lineaCruce"),
    THROUGH_EMA_VWAP_PERIODO_EMA("parameter.throughEmaVwapAlert.periodoEMA"),
    THROUGH_EMA_VWAP_DIRECCION_ROMPIMIENTO("parameter.throughEmaVwapAlert.direccionRompimiento"),
    LINEA_REFERENCIA_EMA_VWAP_SUPPORT("parameter.emaVwapSupportResistance.lineaReferencia"),
    PERIODO_EMA_EMA_VWAP_SUPPORT("parameter.emaVwapSupportResistance.periodoEMA"),
    TIPO_ROL_EMA_VWAP_SUPPORT("parameter.emaVwapSupportResistance.tipoRol"),

    // Parámetros de Tiempo y Patrones de Precio
    TIMEFRAME_BEARISH_BULLISH_ENGULFING_CANDLE("parameter.bearishBullishEngulfingCandle.timeframe"),
    TIPO_PATRON_BEARISH_BULLISH_ENGULFING_CANDLE("parameter.bearishBullishEngulfingCandle.tipoPatron"),
    TIPO_VELA_FIRTS_CANDLE("parameter.firstCandle.tipoVela"),
    NUMERO_VELAS_CONSECUTIVAS("parameter.consecutiveCandles.numeroVelasConsecutivas"),
    TIMEFRAME_CONSECUTIVE_CANDLES("parameter.consecutiveCandles.timeframe"),
    OPCION_EXTREMO_HIGH_LOW_DAY("parameter.highLowOfDay.opcionExtremo"),
    TIMEFRAME_HIGH_LOW_DAY("parameter.highLowOfDay.timeframe"),
    OPCION_EXTREMO_NEW_CANDLE("parameter.newCandleHighLow.opcionExtremo"),
    TIMEFRAME_NEW_CANDLE("parameter.newCandleHighLow.timeframe"),
    PUNTO_REFERENCIA_PULLBACK("parameter.percentagePullbackHighsLows.puntoReferenciaPullback"),
    PORCENTAJE_RETROCESO_PULLBACK("parameter.percentagePullbackHighsLows.porcentajeRetroceso"),
    OPCION_EXTREMO_BREAK_OVER("parameter.breakOverRecentHighsLows.opcionExtremo"),
    TIMEFRAME_BREAK_OVER("parameter.breakOverRecentHighsLows.timeframe"),
    TIMEFRAME_OPENING_RANGE_BREAKDOWN("parameter.openingRangeBreakdown.timeframe"),
    TIMEFRAME_OPENING_RANGE_BREAKOUT("parameter.openingRangeBreakout.timeframe"),
    TIMEFRAME_PIVOTS("parameter.pivots.timeframe"),
    MINUTOS_TRANSCURRIDOS("parameter.minutosInMarket.minutosTranscurridos"),

    // Parámetros de Características Fundamentales
    ESTADO_NOTICIA("parameter.noticias.estadoNoticia");

    private final String etiqueta;

}
