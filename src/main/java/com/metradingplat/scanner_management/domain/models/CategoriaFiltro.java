package com.metradingplat.scanner_management.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.metradingplat.scanner_management.domain.enums.EnumCategoriaFiltro;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaFiltro {
    private String etiqueta;
    private EnumCategoriaFiltro enumCategoriaFiltro;
}
