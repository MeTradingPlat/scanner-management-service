package com.metradingplat.scanner_management.infrastructure.input.controllerGestionarFiltro.DTOAnswer;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.metradingplat.scanner_management.domain.enums.EnumParametro;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParametroDTORespuesta {
    private EnumParametro enumParametro;
    private String etiqueta;
    private ValorDTORespuesta objValorSeleccionado;
    private List<ValorDTORespuesta> opciones;
}
