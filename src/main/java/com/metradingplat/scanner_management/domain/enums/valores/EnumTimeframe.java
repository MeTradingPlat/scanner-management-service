package com.metradingplat.scanner_management.domain.enums.valores;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EnumTimeframe implements IEnumValores {
    // Minutos
    _1M("timeframe.1m"),
    _2M("timeframe.2m"),
    _3M("timeframe.3m"),
    _5M("timeframe.5m"),
    _10M("timeframe.10m"),
    _15M("timeframe.15m"),
    _30M("timeframe.30m"),
    _45M("timeframe.45m"),

    // Horas
    _1H("timeframe.1h"),
    _2H("timeframe.2h"),
    _3H("timeframe.3h"),
    _4H("timeframe.4h"),
    _12H("timeframe.12h"),

    // Días y semanas
    _1D("timeframe.1d"),
    _2D("timeframe.2d"),
    _3D("timeframe.3d"),
    _1W("timeframe.1w"),

    // Meses, trimestres, semestres y años
    _1MO("timeframe.1mo"),
    _3MO("timeframe.3mo"),
    _6MO("timeframe.6mo"),
    _1Y("timeframe.1y");

    private final String etiqueta;

    @Override
    public String getName() {
        return this.name();
    }
}