package com.metradingplat.scanner_management.domain.enums.valores;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EnumLineaCruce implements IEnumValores {
    VWAP("crossLine.vwap"),
    EMA("crossLine.ema");

    private final String etiqueta;

    @Override
    public String getName() {
        return this.name();
    }
}