package com.metradingplat.scanner_management.domain.enums.valores;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumTipoVolumen implements IEnumValores {
    CIERRE("crossLevel.close"),
    APERTURA("crossLevel.open");

    private final String etiqueta;

    @Override
    public String getName() {
        return this.name();
    }

}