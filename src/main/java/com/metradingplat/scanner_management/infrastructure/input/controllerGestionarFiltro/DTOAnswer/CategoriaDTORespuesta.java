package com.metradingplat.scanner_management.infrastructure.input.controllerGestionarFiltro.DTOAnswer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.metradingplat.scanner_management.domain.enums.EnumCategoriaFiltro;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaDTORespuesta {
    private EnumCategoriaFiltro enumCategoriaFiltro;
    private String etiqueta;
}
