package com.metradingplat.scanner_management.infrastructure.input.controllerGestionarEscaner.DTOAnswer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.metradingplat.scanner_management.domain.enums.EnumTipoEjecucion;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TipoEjecucionDTORespuesta {
    private String etiqueta;
    private EnumTipoEjecucion enumTipoEjecucion;
}
