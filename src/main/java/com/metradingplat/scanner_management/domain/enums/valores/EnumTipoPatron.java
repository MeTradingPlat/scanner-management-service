package com.metradingplat.scanner_management.domain.enums.valores;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EnumTipoPatron implements IEnumValores {
    BEARISH("direction.bearish"),
    BULLISH("direction.bullish");

    private final String etiqueta;

    @Override
    public String getName() {
        return this.name();
    }
}