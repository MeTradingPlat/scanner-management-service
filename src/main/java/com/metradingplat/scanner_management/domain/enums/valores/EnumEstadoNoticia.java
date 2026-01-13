package com.metradingplat.scanner_management.domain.enums.valores;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumEstadoNoticia implements IEnumValores {
    COMPRA("news.state.buy"),
    VENTA("news.state.sell"),
    NINGUNA("news.state.none");

    private final String etiqueta;

    @Override
    public String getName() {
        return this.name();
    }
}