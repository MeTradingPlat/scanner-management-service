package com.metradingplat.scanner_management.domain.models;

import java.util.Set;

import com.metradingplat.scanner_management.domain.enums.EnumCategoriaFiltro;
import com.metradingplat.scanner_management.domain.enums.EnumFiltro;
import com.metradingplat.scanner_management.domain.enums.EnumTipoFiltro;

public final class ClasificadorTipoFiltro {

    // Filtros cuya estrategia en signal-processing-service siempre necesita
    // velas reales sin importar la categoria declarada -- debe mantenerse
    // sincronizado con _REQUIERE_VELAS en
    // signal-processing-service/app/scanner/symbols.py.
    private static final Set<EnumFiltro> REQUIERE_VELAS = Set.of(
            EnumFiltro.PERCENTAGE_CHANGE,
            EnumFiltro.CROSSING_ABOVE_BELOW,
            EnumFiltro.VOLUME_SPIKE,
            EnumFiltro.RELATIVE_VOLUME,
            EnumFiltro.RELATIVE_VOLUME_SAME_TIME,
            EnumFiltro.AVERAGE_VOLUME,
            EnumFiltro.DISTANCE_FROM_VWAP,
            EnumFiltro.DISTANCE_FROM_EMA,
            EnumFiltro.DISTANCE_FROM_MA,
            EnumFiltro.BACK_TO_EMA_ALERT,
            EnumFiltro.THROUGH_EMA_VWAP_ALERT,
            EnumFiltro.EMA_VWAP_SUPPORT_RESISTANCE);

    private ClasificadorTipoFiltro() {
    }

    public static EnumTipoFiltro clasificar(EnumFiltro enumFiltro, EnumCategoriaFiltro categoria) {
        if (REQUIERE_VELAS.contains(enumFiltro)) {
            return EnumTipoFiltro.TECNICO;
        }
        if (categoria == EnumCategoriaFiltro.CARACTERISTICAS_FUNDAMENTALES) {
            return EnumTipoFiltro.ESTATICO;
        }
        if (categoria == EnumCategoriaFiltro.PRECIO_Y_MOVIMIENTO || categoria == EnumCategoriaFiltro.VOLUMEN) {
            return EnumTipoFiltro.DINAMICO;
        }
        return EnumTipoFiltro.TECNICO;
    }
}
