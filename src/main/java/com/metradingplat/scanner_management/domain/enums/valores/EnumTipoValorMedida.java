package com.metradingplat.scanner_management.domain.enums.valores;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EnumTipoValorMedida implements IEnumValores {
    PRECIO("valueType.price"),
    PORCENTAJE("valueType.percentage");

    private final String etiqueta;

    @Override
    public String getName() {
        return this.name();
    }
}