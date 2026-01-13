package com.metradingplat.scanner_management.domain.enums.valores;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EnumPuntoReferencia implements IEnumValores {
    OPEN("crossLevel.open"),
    CLOSE("crossLevel.close"),
    CLOSE_POST_MARKET("referencePoint.closePostMarket"),
    CLOSE_PRE_MARKET("referencePoint.closePreMarket");

    private final String etiqueta;

    @Override
    public String getName() {
        return this.name();
    }
}