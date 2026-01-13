package com.metradingplat.scanner_management.domain.enums.valores;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumDireccionRompimiento implements IEnumValores {
    ABOVE("direction.above"),
    BELOW("direction.below");

    private final String etiqueta;

    @Override
    public String getName() {
        return this.name();
    }
}