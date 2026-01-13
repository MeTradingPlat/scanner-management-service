package com.metradingplat.scanner_management.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumTipoEjecucion {
    UNA_VEZ("execution.once"),
    DIARIA("execution.daily"),;

    private final String etiqueta;
}