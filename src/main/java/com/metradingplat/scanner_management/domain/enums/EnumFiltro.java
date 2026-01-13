package com.metradingplat.scanner_management.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumFiltro {
    // Categoría: Volumen
    VOLUME("filter.volume.name", "filter.volume.description"),
    AVERAGE_VOLUME("filter.averageVolume.name", "filter.averageVolume.description"),
    VOLUMEN_POST_PRE("filter.volumenPostPre.name", "filter.volumenPostPre.description"),
    RELATIVE_VOLUME("filter.relativeVolume.name", "filter.relativeVolume.description"),
    RELATIVE_VOLUME_SAME_TIME("filter.relativeVolumeSameTime.name", "filter.relativeVolumeSameTime.description"),
    VOLUME_SPIKE("filter.volumeSpike.name", "filter.volumeSpike.description"),

    // Categoría: Precio y Movimiento
    CHANGE("filter.change.name", "filter.change.description"),
    PERCENTAGE_CHANGE("filter.percentageChange.name", "filter.percentageChange.description"),
    PRECIO("filter.precio.name", "filter.precio.description"),
    GAP_FROM_CLOSE("filter.gapFromClose.name", "filter.gapFromClose.description"),
    POSITION_IN_RANGE("filter.positionInRange.name", "filter.positionInRange.description"),
    PERCENTAGE_RANGE("filter.percentageRange.name", "filter.percentageRange.description"),
    RANGE_DOLLARS("filter.rangeDollars.name", "filter.rangeDollars.description"),
    CROSSING_ABOVE_BELOW("filter.crossingAboveBelow.name", "filter.crossingAboveBelow.description"),
    HALT("filter.halt.name", "filter.halt.description"),

    // Categoría: Volatilidad
    ATR("filter.atr.name", "filter.atr.description"),
    ATRP("filter.atrp.name", "filter.atrp.description"),
    RELATIVE_RANGE("filter.relativeRange.name", "filter.relativeRange.description"),

    // Categoría: Momentum e Indicadores Técnicos
    RSI("filter.rsi.name", "filter.rsi.description"),
    DISTANCE_FROM_VWAP("filter.distanceFromVwap.name", "filter.distanceFromVwap.description"),
    DISTANCE_FROM_EMA("filter.distanceFromEma.name", "filter.distanceFromEma.description"),
    DISTANCE_FROM_MA("filter.distanceFromMa.name", "filter.distanceFromMa.description"),
    BACK_TO_EMA_ALERT("filter.backToEmaAlert.name", "filter.backToEmaAlert.description"),
    THROUGH_EMA_VWAP_ALERT("filter.throughEmaVwapAlert.name", "filter.throughEmaVwapAlert.description"),
    EMA_VWAP_SUPPORT_RESISTANCE("filter.emaVwapSupportResistance.name", "filter.emaVwapSupportResistance.description"),

    // Categoría: Tiempo y Patrones de Precio
    BEARISH_BULLISH_ENGULFING("filter.bearishBullishEngulfing.name", "filter.bearishBullishEngulfing.description"),
    CONSECUTIVE_CANDLES("filter.consecutiveCandles.name", "filter.consecutiveCandles.description"),
    FIRST_CANDLE("filter.firstCandle.name", "filter.firstCandle.description"),
    HIGH_LOW_OF_DAY("filter.highLowOfDay.name", "filter.highLowOfDay.description"),
    NEW_CANDLE_HIGH_LOW("filter.newCandleHighLow.name", "filter.newCandleHighLow.description"),
    PERCENTAGE_PULLBACK_HIGHS_LOWS("filter.percentagePullbackHighsLows.name",
            "filter.percentagePullbackHighsLows.description"),
    BREAK_OVER_RECENT_HIGHS_LOWS("filter.breakOverRecentHighsLows.name", "filter.breakOverRecentHighsLows.description"),
    OPENING_RANGE_BREAKDOWN("filter.openingRangeBreakdown.name", "filter.openingRangeBreakdown.description"),
    OPENING_RANGE_BREAKOUT("filter.openingRangeBreakout.name", "filter.openingRangeBreakout.description"),
    PIVOTS("filter.pivots.name", "filter.pivots.description"),
    MINUTOS_IN_MARKET("filter.minutosInMarket.name", "filter.minutosInMarket.description"),

    // Categoría: Características Fundamentales
    FLOAT("filter.float.name", "filter.float.description"),
    SHARES_OUTSTANDING("filter.sharesOutstanding.name", "filter.sharesOutstanding.description"),
    MARKET_CAP("filter.marketCap.name", "filter.marketCap.description"),
    SHORT_INTEREST("filter.shortInterest.name", "filter.shortInterest.description"),
    SHORT_RATIO("filter.shortRatio.name", "filter.shortRatio.description"),
    DAYS_UNTIL_EARNINGS("filter.daysUntilEarnings.name", "filter.daysUntilEarnings.description"),
    NOTICIAS("filter.noticias.name", "filter.noticias.description"),

    // Filtro no encontrado
    UNKNOWN("filter.unknown.name", "filter.unknown.description"),
    ;

    private final String etiquetaNombre;
    private final String etiquetaDescripcion;
}