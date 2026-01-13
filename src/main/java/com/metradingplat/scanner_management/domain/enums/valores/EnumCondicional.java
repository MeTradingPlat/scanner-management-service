package com.metradingplat.scanner_management.domain.enums.valores;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumCondicional implements IEnumValores {
    MAYOR_QUE("condition.greaterThan"),
    MENOR_QUE("condition.lessThan"),
    ENTRE("condition.between"),
    FUERA("condition.outside"),
    IGUAL_A("condition.equalTo");

    private final String etiqueta;

    @Override
    public String getName() {
        return this.name();
    }
}