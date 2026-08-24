package com.metradingplat.scanner_management.domain.enums.valores;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumTipoVolumenPostPre implements IEnumValores {
    PRE("volumenPostPre.pre"),
    POST("volumenPostPre.post"),
    AMBOS("volumenPostPre.ambos");

    private final String etiqueta;

    @Override
    public String getName() {
        return this.name();
    }

}
