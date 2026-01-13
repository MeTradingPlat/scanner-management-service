package com.metradingplat.scanner_management.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumCategoriaFiltro {
    TODOS("category.all"),
    VOLUMEN("category.volume"),
    PRECIO_Y_MOVIMIENTO("category.price"),
    VOLATILIDAD("category.volatility"),
    MOMENTUM_E_INDICADORES_TECNICOS("category.momentum"),
    CARACTERISTICAS_FUNDAMENTALES("category.fundamental"),
    TIEMPO_Y_PATRONES_DE_PRECIO("category.patterns");

    private final String etiqueta;
}