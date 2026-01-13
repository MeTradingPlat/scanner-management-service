package com.metradingplat.scanner_management.domain.enums.valores;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EnumOpcionExtremo implements IEnumValores {
    HIGH("extremeOption.high"),
    LOW("extremeOption.low");

    private final String etiqueta;

    @Override
    public String getName() {
        return this.name();
    }
}