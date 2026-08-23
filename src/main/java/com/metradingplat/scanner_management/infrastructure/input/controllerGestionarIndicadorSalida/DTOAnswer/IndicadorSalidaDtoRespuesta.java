package com.metradingplat.scanner_management.infrastructure.input.controllerGestionarIndicadorSalida.DTOAnswer;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.metradingplat.scanner_management.domain.enums.EnumIndicadorSalida;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IndicadorSalidaDtoRespuesta {
    private EnumIndicadorSalida enumIndicadorSalida;
    private String etiquetaNombre;
    private String etiquetaDescripcion;
    private List<ParametroIndicadorSalidaDTORespuesta> parametros;
}
