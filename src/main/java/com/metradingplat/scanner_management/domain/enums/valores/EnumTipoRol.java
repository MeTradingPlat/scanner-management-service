package com.metradingplat.scanner_management.domain.enums.valores;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EnumTipoRol implements IEnumValores {
    RESISTENCIA("roleType.resistance"),
    SOPORTE("roleType.support");

    private final String etiqueta;

    @Override
    public String getName() {
        return this.name();
    }
}