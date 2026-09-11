package com.metradingplat.scanner_management.domain.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.metradingplat.scanner_management.domain.enums.EnumCategoriaFiltro;
import com.metradingplat.scanner_management.domain.enums.EnumFiltro;
import com.metradingplat.scanner_management.domain.enums.EnumTipoFiltro;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Filtro {
    private EnumFiltro enumFiltro;
    private String etiquetaNombre;
    private String etiquetaDescripcion;
    private CategoriaFiltro objCategoria;
    private List<Parametro> parametros;

    // Solo tiene sentido para filtros de temporalidad corta (M1/M3): si es
    // true, signal-processing-service revisa este filtro contra el WS de
    // velas en vivo de marketdata-service en vez de esperar al ciclo
    // normal (~60s). Ver realtime_candle_client.py.
    private boolean revisionTiempoReal;

    // null = filtro requerido (AND estricto, comportamiento de siempre). Dos
    // o mas filtros con el MISMO valor no nulo forman un grupo alternativo
    // dentro de su grupo de temporalidad: basta con que UNO de ellos pase
    // (ver SymbolPipeline._todos_los_requeridos_pasan en signal-processing-service).
    private Integer grupoAlternativo;

    public EnumTipoFiltro getEnumTipoFiltro() {
        EnumCategoriaFiltro categoria = this.objCategoria != null ? this.objCategoria.getEnumCategoriaFiltro() : null;
        return ClasificadorTipoFiltro.clasificar(this.enumFiltro, categoria);
    }
}