package com.metradingplat.scanner_management.domain.enums.valores;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumCondicionalBoolean implements IEnumValores {
    VERDADERO("condition.true"),
    FALSO("condition.false");

    private final String etiqueta;

    @Override
    public String getName() {
        return this.name();
    }
}