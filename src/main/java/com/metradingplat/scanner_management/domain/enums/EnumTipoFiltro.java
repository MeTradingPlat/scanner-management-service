package com.metradingplat.scanner_management.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumTipoFiltro {
    ESTATICO("filterType.static"),
    DINAMICO("filterType.dynamic"),
    TECNICO("filterType.technical");

    private final String etiqueta;
}
