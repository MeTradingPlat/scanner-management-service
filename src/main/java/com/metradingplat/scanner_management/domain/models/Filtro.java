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

    public EnumTipoFiltro getEnumTipoFiltro() {
        EnumCategoriaFiltro categoria = this.objCategoria != null ? this.objCategoria.getEnumCategoriaFiltro() : null;
        return ClasificadorTipoFiltro.clasificar(this.enumFiltro, categoria);
    }
}