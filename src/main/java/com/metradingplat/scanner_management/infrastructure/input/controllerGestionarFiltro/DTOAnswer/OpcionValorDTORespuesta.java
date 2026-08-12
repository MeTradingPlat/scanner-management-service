package com.metradingplat.scanner_management.infrastructure.input.controllerGestionarFiltro.DTOAnswer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OpcionValorDTORespuesta {
    private Number valor;
    private String etiquetaClave;
    private String etiqueta;
}
