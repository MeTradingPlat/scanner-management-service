package com.metradingplat.scanner_management.infrastructure.input.controllerGestionarIndicadorSalida.DTOAnswer;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.metradingplat.scanner_management.domain.enums.EnumParametroIndicadorSalida;
import com.metradingplat.scanner_management.infrastructure.input.controllerGestionarFiltro.DTOAnswer.ValorDTORespuesta;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParametroIndicadorSalidaDTORespuesta {
    private EnumParametroIndicadorSalida enumParametroIndicadorSalida;
    private String etiqueta;
    private ValorDTORespuesta objValorSeleccionado;
    private List<ValorDTORespuesta> opciones;
}
